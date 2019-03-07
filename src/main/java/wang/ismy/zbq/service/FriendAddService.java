package wang.ismy.zbq.service;

import org.apache.tomcat.util.http.fileupload.ThresholdingOutputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.dao.FriendAddMapper;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.entity.FriendAdd;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FriendAddService {

    @Autowired
    private FriendAddMapper friendAddMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private MessageService messageService;

    public int insertNew(FriendAddDTO friendAddDTO) {


        User from = User.builder().userId(friendAddDTO.getFromUser()).build();

        if (userService.selectByPrimaryKey(friendAddDTO.getToUser()) == null) {
            ErrorUtils.error(StringResources.TARGET_USER_NOT_EXIST);
        }

        User to = User.builder().userId(friendAddDTO.getToUser()).build();

        FriendAdd friendAdd = FriendAdd.builder()
                .fromUser(from)
                .toUser(to)
                .msg(friendAddDTO.getMsg()).build();
        return friendAddMapper.insertNew(friendAdd);
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


    @Transactional
    public void agreeFriendAddMsg(Integer friendAddId) {

        FriendAdd friendAdd = friendAddMapper.selectFriendAddByPrimaryKey(friendAddId);

        if (friendAdd == null) {
            ErrorUtils.error(StringResources.FRIEND_ADD_RECORD_NOT_EXIST);
        }

        User user = userService.getCurrentUser();
        // 如果当前登录的用户非friendAdd记录中的toUser，则拒绝
        if (!user.getUserId().equals(friendAdd.getToUser().getUserId())) {
            ErrorUtils.error(StringResources.PERMISSION_DENIED);
        }

        // 插入to和from的双向联系
        if (friendService.insertNewRelation(friendAdd.getFromUser().getUserId(), friendAdd.getToUser().getUserId()) != 1) {
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

        if (friendService.insertNewRelation(friendAdd.getToUser().getUserId(), friendAdd.getFromUser().getUserId()) != 1) {
            ErrorUtils.error(StringResources.UNKNOWN_ERROR);
        }

        // 将friendAdd记录置为不可见
        friendAddMapper.updateVisible(friendAddId);

        // 以to的身份给from发送一条消息，告知其已是好友
        //TODO
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(friendAdd.getFromUser().getUserId());
        messageDTO.setContent("我已通过了你的好友请求");
        messageService.currentUserSendMessage(messageDTO);
    }
}
