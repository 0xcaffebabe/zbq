package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.dto.message.UnreadMessageDTO;
import wang.ismy.zbq.model.vo.user.UserVO;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.model.vo.message.MessageListVO;
import wang.ismy.zbq.model.vo.message.MessageVO;
import wang.ismy.zbq.model.entity.Message;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.model.vo.message.UnreadMessageVO;
import wang.ismy.zbq.util.TimeUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Setter(onMethod_ = @Inject)
@Slf4j
public class MessageService {

    private MessageMapper mapper;

    private UserService userService;

    private FriendService friendService;

    private ExecuteService executeService;

    private TemplateEngineService templateEngineService;

    private EmailService emailService;

    public List<MessageVO> selectCurrentUserMessageListByFriendId(int friendId) {
        User user = userService.getCurrentUser();

        if (!friendService.isFriend(user.getUserId(), friendId)) {
            ErrorUtils.error(R.NOT_FRIEND);
        }

        User friend = userService.selectByPrimaryKey(friendId);

        var list = mapper.selectMessageListBy2User(friendId, user.getUserId());

        List<MessageVO> ret = convertToMessageVO(user, friend, list);
        // 当拉取所有消息之后，将friendId发送给用户与的所有设置为已读
        updateHasRead(user.getUserId(), friendId);
        return ret;

    }

    public boolean currentUserSendMessage(MessageDTO messageDTO) {

        User user = userService.getCurrentUser();
        return sendMessage(user, messageDTO);

    }

    public boolean sendMessage(User fromUser, MessageDTO messageDTO) {
        if (!friendService.isFriend(messageDTO.getTo(), fromUser.getUserId())) {
            ErrorUtils.error(R.NOT_FRIEND);
        }
        User t = new User();
        t.setUserId(messageDTO.getTo());
        Message message = new Message();
        message.setFromUser(fromUser);
        message.setToUser(t);
        message.setContent(messageDTO.getContent());

        // 给To发送一封邮件
        executeService.submit(() -> messageInform(messageDTO.getTo(), fromUser, messageDTO.getContent()));
        return mapper.insert(message) == 1;
    }

    public void messageInform(Integer to, User fromUser, String msg) {
        var toUser = userService.selectByPrimaryKey(to);
        if (toUser == null) {
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }


        try {
            String html = templateEngineService.parseModel("email/messageInform.html",
                    Map.of(
                            "user", fromUser.getUserInfo().getNickName(),
                            "time", TimeUtils.getStrTime(),
                            "content", msg
                    ));
            emailService.sendHtmlMail(to, "【转笔圈】你有一条朋友消息", html);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("发送邮件发送异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

    public List<UnreadMessageVO> selectCurrentUserUnreadMessageList() {

        User user = userService.getCurrentUser();

        var list = mapper.selectUnreadMessageByUserId(user.getUserId());
        List<Integer> userIdList = new ArrayList<>();
        for (var i : list) {
            userIdList.add(i.getFromUser());
        }
        if (userIdList.size() == 0) {
            return List.of();
        }

        var userList = userService.selectByUserIdBatch(userIdList);

        List<UnreadMessageVO> ret = new ArrayList<>();
        copyProperties(list, userList, ret);
        return ret;

    }

    /**
     * TODO 实现有问题，待修改重构
     */
    public List<MessageListVO> selectCurrentUserMessageList() {

        User user = userService.getCurrentUser();

        List<Integer> friendIdList = getFriendIdList(user.getUserId());
        Map<Integer, UserVO> userVOMap = UserService.userList2UserVOMap(userService.selectByUserIdBatch(friendIdList));

        var messageList = mapper.selectRecentMessageList(user.getUserId(), friendIdList);
        var unreadMessageList = selectCurrentUserUnreadMessageList();

        List<MessageListVO> ret = new ArrayList<>();
        Map<Integer,Boolean> contactUserMap = new HashMap<>();

        for (var i :unreadMessageList){
            MessageListVO vo = new MessageListVO();
            vo.setOppositeSideId(i.getFromUserId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(i.getFromUserInfo(),userVO);
            vo.setOppositeSideUserInfo(userVO);
            vo.setNewestMsg(i.getNewestMsg());
            vo.setMsgCount(i.getMsgCount());
            ret.add(vo);
            contactUserMap.put(i.getFromUserId(),true);
        }

        for (var i : messageList) {
            MessageListVO vo = new MessageListVO();
            if (!i.getFromUser().equals(user.getUserId())) {

                vo.setOppositeSideId(i.getFromUser());
                vo.setOppositeSideUserInfo(userVOMap.get(i.getFromUser()));
            }else{
                vo.setOppositeSideId(i.getToUser());
                vo.setOppositeSideUserInfo(userVOMap.get(i.getToUser()));
            }
            vo.setNewestMsg(i.getNewestMsg());
            if (contactUserMap.get(vo.getOppositeSideId()) == null){
                ret.add(vo);
                contactUserMap.put(vo.getOppositeSideId(),true);
            }

        }

        // 对结果进行排序
        return ret.stream()
                .sorted(Comparator.comparingInt(e -> friendIdList.indexOf(e.getOppositeSideId())))
                .collect(Collectors.toList());

    }



    public void updateHasRead(int userId, int friendId) {
        if (friendService.isFriend(userId, friendId)) {
            mapper.updateHasRead(userId, friendId);
        } else {
            ErrorUtils.error(R.NOT_FRIEND);
        }

    }

    private List<MessageVO> convertToMessageVO(User user, User friend, List<Message> list) {
        List<MessageVO> ret = new ArrayList<>();
        for (var i : list) {
            MessageVO messageVO = new MessageVO();
            messageVO.setSenderId(i.getFromUser().getUserId());
            messageVO.setSenderInfo(user.equals(i.getFromUser()) ? user.getUserInfo() : friend.getUserInfo());
            messageVO.setContent(i.getContent());
            messageVO.setSendTime(i.getCreateTime());
            ret.add(messageVO);
        }
        return ret;
    }

    private void copyProperties(List<UnreadMessageDTO> list, List<User> userList, List<UnreadMessageVO> ret) {
        for (var i : list) {

            for (var j : userList) {

                if (j.getUserId().equals(i.getFromUser())) {
                    UnreadMessageVO vo = new UnreadMessageVO();
                    vo.setFromUserInfo(j.getUserInfo());
                    vo.setMsgCount(i.getMsgCount());
                    vo.setNewestMsg(i.getNewestMsg());
                    vo.setFromUserId(i.getFromUser());
                    vo.setSendTime(i.getSendTime());
                    ret.add(vo);
                    break;
                }
            }
        }
    }

    private List<Integer> getFriendIdList(Integer userId) {
        var list = mapper.selectRecentMessageUser(userId);
        List<Integer> ret = new ArrayList<>();

        for (var map : list) {
            var fromUser = map.get("from_user");

            if (!fromUser.equals(userId)) {
                if (!ret.contains(fromUser)) {
                    ret.add(fromUser);
                }
            } else {
                var toUser = map.get("to_user");
                if (!ret.contains(toUser)) {
                    ret.add(toUser);
                }
            }
        }
        return ret;
    }
}
