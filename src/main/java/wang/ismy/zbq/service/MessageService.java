package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.vo.MessageVO;
import wang.ismy.zbq.vo.SenderVO;
import wang.ismy.zbq.entity.Message;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.vo.UnreadMessageVO;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {


    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    public List<MessageVO> selectCurrentUserMessageListByFriendId(int friendId){
        User user = userService.getCurrentUser();

        if (!friendService.isFriend(user.getUserId(),friendId)){
            ErrorUtils.error(StringResources.NOT_FRIEND);
        }

        User friend = userService.selectByPrimaryKey(friendId);

        var list = messageMapper.selectMessageListBy2User(friendId,user.getUserId());

        List<MessageVO> ret = new ArrayList<>();
        for (var i : list){

            SenderVO senderVO = SenderVO.builder()
                    .userId(i.getFromUser().getUserId())
                    .senderName(user.equals(i.getFromUser())?user.getUserInfo().getNickName():friend.getUserInfo().getNickName())
                    .build();
            MessageVO messageVO = MessageVO.builder()
                    .sender(senderVO)
                    .content(i.getContent())
                    .sendTime(i.getCreateTime())
                    .build();
            ret.add(messageVO);
        }
        return ret;

    }

    public boolean currentUserSendMessage(MessageDTO messageDTO) {

        User user = userService.getCurrentUser();

        if (!friendService.isFriend(messageDTO.getTo(),user.getUserId())){
            ErrorUtils.error(StringResources.NOT_FRIEND);
        }

        Message message = Message.builder()
                .fromUser(user)
                .toUser(User.builder().userId(messageDTO.getTo()).build())
                .content(messageDTO.getContent())
                .build();

        return messageMapper.insert(message) == 1;

    }

    public Object selectCurrentUserUnreadMessageList() {

        User user = userService.getCurrentUser();

        var list = messageMapper.selectUnreadMessageByUserId(user.getUserId());
        List<Integer> userIdList = new ArrayList<>();
        for (var i : list){
            userIdList.add(i.getFromUser());
        }
        if (userIdList.size() == 0) return list;

        var userList = userService.selectByUserIdBatch(userIdList);

        List<UnreadMessageVO> ret = new ArrayList<>();
        for (var i : list){

            for (var j : userList){

                if (j.getUserId().equals(i.getFromUser())){
                    UnreadMessageVO vo = new UnreadMessageVO();
                    vo.setFromUserInfo(j.getUserInfo());
                    vo.setMsgCount(i.getMsgCount());
                    vo.setNewestMsg(i.getNewestMsg());
                    ret.add(vo);
                }
            }
        }
        return ret;

    }
}
