package wang.ismy.zbq.service.friend;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.dao.friend.FriendAddMapper;
import wang.ismy.zbq.model.dto.FriendAddDTO;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.entity.friend.FriendAdd;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.TemplateEngineService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.util.TimeUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author my
 */
@Service
@Slf4j
@Setter(onMethod_ = @Inject)
public class FriendAddService {

    private FriendAddMapper friendAddMapper;

    private UserService userService;

    private FriendService friendService;

    private MessageService messageService;

    private InformService informService;

    private ExecuteService executeService;

    private EmailService emailService;

    private TemplateEngineService templateEngineService;

    public int insertNew(FriendAddDTO friendAddDTO) {

        User from = new User();
        from.setUserId(friendAddDTO.getFromUser());

        if (userService.selectByPrimaryKey(friendAddDTO.getToUser()) == null) {
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }

        User to = new User();
        to.setUserId(friendAddDTO.getToUser());

        FriendAdd friendAdd = new FriendAdd();
                friendAdd.setFromUser(from);
                friendAdd.setToUser(to);
                friendAdd.setMsg(friendAddDTO.getMsg());

        return friendAddMapper.insertNew(friendAdd);
    }

    public void friendInform(Integer to, User fromUser, String msg) {
        var toUser = userService.selectByPrimaryKey(to);

        if (toUser == null){
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }

        try {
            String html = templateEngineService.parseModel("email/friendInform.html", Map.of(
                    "user",fromUser.getUserInfo().getNickName(),
                    "time", TimeUtils.getStrTime(),
                    "content",msg
            ));
            emailService.sendHtmlMail(to,"【转笔圈】有人申请添加你为笔友",html);
        } catch (Exception e) {
            log.error("发送邮件发生异常:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    public FriendAdd selectFriendAddByFromUserAndToUser(Integer from, Integer to) {
        return friendAddMapper.selectFriendAddByFromUserAndToUser(from, to);
    }

    public List<FriendAdd> selectFriendAddListByToUser(Integer toUserId) {
        var list = friendAddMapper.selectFriendAddListByToUserId(toUserId);
        List<Integer> userIdList = new ArrayList<>();
        User toUser = userService.selectByPrimaryKey(toUserId);
        for (var i : list) {
            userIdList.add(i.getFromUser().getUserId());
        }

        if (list.size() == 0) {
            return list;
        }

        List<User> users = userService.selectByUserIdBatch(userIdList);

        // 将查询出来的用户分别对应给朋友添加消息的toUser和fromUser
        for (var i : list) {
            i.setToUser(toUser);
            for (var j : users) {
                if (j.getUserId().equals(i.getFromUser().getUserId())) {
                    i.setFromUser(j);
                }
            }
        }
        return list;

    }


    @Transactional(rollbackFor = Exception.class)
    public void agreeFriendAddMsg(Integer friendAddId) {

        FriendAdd friendAdd = friendAddMapper.selectFriendAddByPrimaryKey(friendAddId);

        checkFriendAddIsCurrentUser(friendAdd);


        createFriendRelation(friendAdd);

        // 将friendAdd记录置为不可见
        friendAddMapper.updateVisible(friendAddId);

        sendFriendAddPassMessage(friendAdd);
    }

    @Transactional(rollbackFor = Exception.class)
    public void rejectFriendAdd(Integer friendAddId) {

        FriendAdd friendAdd = friendAddMapper.selectFriendAddByPrimaryKey(friendAddId);
        checkFriendAddIsCurrentUser(friendAdd);
        // 将friendAdd记录置为不可见
        friendAddMapper.updateVisible(friendAddId);

        //使用系统账号向fromUser发送一条被拒绝的消息
        User user = userService.selectByPrimaryKey(friendAdd.getToUser().getUserId());
        informService.informUser(friendAdd.getFromUser().getUserId(),user.getUserInfo().getNickName()+"拒绝了你的好友添加请求");


    }

    private void checkFriendAddIsCurrentUser(FriendAdd friendAdd) {
        if (friendAdd == null) {
            ErrorUtils.error(R.FRIEND_ADD_RECORD_NOT_EXIST);
        }

        User user = userService.getCurrentUser();
        // 如果当前登录的用户非friendAdd记录中的toUser，则拒绝
        if (!user.getUserId().equals(friendAdd.getToUser().getUserId())) {
            ErrorUtils.error(R.PERMISSION_DENIED);
        }
    }

    private void sendFriendAddPassMessage(FriendAdd friendAdd) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(friendAdd.getFromUser().getUserId());
        messageDTO.setContent("我已通过了你的好友请求");
        messageService.currentUserSendMessage(messageDTO);
    }

    private void createFriendRelation(FriendAdd friendAdd) {
        if (friendService.insertNewRelation(friendAdd.getFromUser().getUserId(), friendAdd.getToUser().getUserId()) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

        if (friendService.insertNewRelation(friendAdd.getToUser().getUserId(), friendAdd.getFromUser().getUserId()) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
    }



}
