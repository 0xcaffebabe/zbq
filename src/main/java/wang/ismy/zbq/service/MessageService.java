package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.dto.UnreadMessageDTO;
import wang.ismy.zbq.vo.MessageListVO;
import wang.ismy.zbq.vo.MessageVO;
import wang.ismy.zbq.entity.Message;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.UnreadMessageVO;

import java.util.*;

@Service
public class MessageService {


    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    public List<MessageVO> selectCurrentUserMessageListByFriendId(int friendId) {
        User user = userService.getCurrentUser();

        if (!friendService.isFriend(user.getUserId(), friendId)) {
            ErrorUtils.error(R.NOT_FRIEND);
        }

        User friend = userService.selectByPrimaryKey(friendId);

        var list = messageMapper.selectMessageListBy2User(friendId, user.getUserId());

        List<MessageVO> ret = convertToMessageVO(user, friend, list);
        // 当拉取所有消息之后，将friendId发送给用户与的所有设置为已读
        updateHasRead(user.getUserId(),friendId);
        return ret;

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

    public boolean currentUserSendMessage(MessageDTO messageDTO) {

        User user = userService.getCurrentUser();

        if (!friendService.isFriend(messageDTO.getTo(), user.getUserId())) {
            ErrorUtils.error(R.NOT_FRIEND);
        }

        User t = new User();
        t.setUserId(messageDTO.getTo());
        Message message = new Message();
                message.setFromUser(user);
                message.setToUser(t);
                message.setContent(messageDTO.getContent());


        return messageMapper.insert(message) == 1;

    }

    public boolean sendMessage(User fromUser,MessageDTO messageDTO){
        if (!friendService.isFriend(messageDTO.getTo(), fromUser.getUserId())) {
            ErrorUtils.error(R.NOT_FRIEND);
        }
        User t= new User();
        t.setUserId(messageDTO.getTo());
        Message message = new Message();
                message.setFromUser(fromUser);
                message.setToUser(t);
                message.setContent(messageDTO.getContent());

        return messageMapper.insert(message) == 1;
    }

    public List<UnreadMessageVO> selectCurrentUserUnreadMessageList() {

        User user = userService.getCurrentUser();

        var list = messageMapper.selectUnreadMessageByUserId(user.getUserId());
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

    public List<MessageListVO> selectCurrentUserMessageList() {

        User user = userService.getCurrentUser();

        // 查询出当前用户通信列表
        var messageList = messageMapper.selectMessageListByUserId(user.getUserId());
        // 查询出当前用户未读消息列表
        var unreadList = selectCurrentUserUnreadMessageList();
        List<MessageListVO> ret = new ArrayList<>();

        // 该map的作用是防止同一对用户被重复加入
        Map<Integer, Integer> judgeMap = new HashMap<>();

        // 先把所有未读消息插入到结果中
        for (var i : unreadList) {
            judgeMap.put(i.getFromUserId(), user.getUserId());
            MessageListVO vo = new MessageListVO();
            vo.setOppositeSideId(i.getFromUserId());
            vo.setOppositeSideUserInfo(i.getFromUserInfo());
            vo.setMsgCount(i.getMsgCount());
            vo.setNewestMsg(i.getNewestMsg());
            ret.add(vo);
        }

        List<Integer> userIdList = new ArrayList<>();


        for (var i : messageList) {
            // 如果发送方为当前登录用户，则对方为toUser
            Integer oppositeUser = null;
            if (i.getFromUser().equals(user.getUserId())) {
                oppositeUser = i.getToUser();
            } else {
                oppositeUser = i.getFromUser();
            }
            userIdList.add(oppositeUser);


            // 如果judgeMap当中查找不到oppositeUser与当前登录用户的映射关系，则将这条消息加入到结果中
            if (judgeMap.get(oppositeUser) != null) {
                continue;
            }
            MessageListVO vo = new MessageListVO();
            vo.setOppositeSideId(oppositeUser);
            vo.setMsgCount(0);
            vo.setNewestMsg(i.getNewestMsg());
            ret.add(vo);
            judgeMap.put(oppositeUser,user.getUserId());
        }

        var userList = userService.selectByUserIdBatch(userIdList);

        for (var i : userList){
            for (var j : ret){
                if (j.getOppositeSideUserInfo() == null){
                    if (i.getUserId().equals(j.getOppositeSideId())){
                        j.setOppositeSideUserInfo(i.getUserInfo());
                    }
                }
            }
        }
        return ret;
    }

    public void updateHasRead(int userId, int friendId){
        if (friendService.isFriend(userId,friendId)){
            messageMapper.updateHasRead(userId,friendId);
        }else{
            ErrorUtils.error(R.NOT_FRIEND);
        }

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
}
