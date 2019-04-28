package wang.ismy.zbq.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import wang.ismy.zbq.dao.BroadcastMapper;
import wang.ismy.zbq.dto.BroadcastDTO;
import wang.ismy.zbq.entity.Broadcast;
import wang.ismy.zbq.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.BroadcastVO;
import wang.ismy.zbq.vo.user.UserVO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
public class BroadcastService {

    @Autowired
    private BroadcastMapper broadcastMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ExecuteService executeService;

    private List<WebSocketSession> onlineList = new LinkedList<>();

    private Map<WebSocketSession, HttpSession> sessionMap = new ConcurrentHashMap<>();

    private Map<WebSocketSession,Long> liveMap = new HashMap<>();

    public void currentUserBroadcast(BroadcastDTO dto) {
        var currentUser = userService.getCurrentUser();

        Broadcast broadcast = new Broadcast();
        BeanUtils.copyProperties(dto, broadcast);

        broadcast.setUser(currentUser);
        broadcast.setCreateTime(LocalDateTime.now());
        //TODO 向各客户端推送消息
        broadcast(broadcast);
        if (broadcastMapper.insertNew(broadcast) != 1) {
            ErrorUtils.error(R.BROADCAST_FAIL);
        }

    }

    public List<BroadcastVO> selectNewest10() {

        var currentUser = userService.getCurrentUser();
        var list = broadcastMapper.selectNewest10();

        var userIdList = list.stream()
                .map(broadcast -> broadcast.getUser().getUserId())
                .collect(Collectors.toList());


        var userList = userService.selectByUserIdBatch(userIdList);
        Map<Integer, UserVO> userVOMap = UserService.userList2UserVOMap(userList);

        List<BroadcastVO> ret = new ArrayList<>();
        list.forEach(e -> {
            BroadcastVO vo = BroadcastVO.convert(e, userVOMap.get(e.getUser().getUserId()));

            if (e.getUser().equals(currentUser)) {
                vo.setSelf(true);
            } else {
                vo.setSelf(false);
            }

            ret.add(vo);
        });

        return ret;
    }

    public void addClient(WebSocketSession session, HttpSession httpSession) {
        onlineList.add(session);
        executeService.submit(() -> {
            sessionMap.put(session, httpSession);
            userChanged();
        });
    }

    public void removeClient(WebSocketSession session) {
        onlineList.remove(session);
        executeService.submit(() -> {
            sessionMap.remove(session);
            liveMap.remove(session);
            log.info("情况:{}",sessionMap);
            userChanged();
        });
    }

    public void broadcast(Broadcast broadcast) {

        BroadcastVO vo = BroadcastVO.convert(broadcast, UserVO.convert(userService.getCurrentUser()));


        String json = new Gson().toJson(vo);
        TextMessage message = new TextMessage(json);

        onlineList.forEach(e -> executeService.submit(() -> {
            sendMsg(message, e);
        }));
    }

    public void userChanged() {

        List<HttpSession> tmpList = new ArrayList<>(sessionMap.values());

        log.info("用户数量改变:{}",tmpList);
        var userVOList = tmpList.stream()
                .map((httpSession) -> {
                    try{
                        var user = (User) httpSession.getAttribute("user");
                        return UserVO.convert(user);
                    }catch (Exception e){
                        return UserVO.empty();
                    }

                })
                .collect(Collectors.toList());

        userVOList.removeIf(e->e.getUserId().equals(-1));
        log.info("用户数量改变:{}",userVOList);
        String json = new Gson().toJson(userVOList);

        TextMessage msg = new TextMessage(json);

        onlineList.forEach(e -> executeService.submit(() -> {
            sendMsg(msg, e);
        }));
    }

    private void sendMsg(TextMessage msg, WebSocketSession e) {
        try {
            e.sendMessage(msg);
            log.info("发送一条消息给{},{}", e,msg);
        } catch (IOException e1) {
            log.error("{}发送消息发送错误{}", e, e1.getMessage());
        }
    }

    public void heartBeat(WebSocketSession session) {

    }
}