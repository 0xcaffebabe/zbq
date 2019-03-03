package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.entity.Message;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.List;

@Service
public class MessageService {


    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    public List<Message> selectCurrentUserMessageListByFriendId(int friendId){
        User user = userService.getCurrentUser();
        if (!friendService.isFriend(user.getUserId(),friendId)){
            ErrorUtils.error(StringResources.NOT_FRIEND);
        }

        return messageMapper.selectMessageListBy2User(friendId,user.getUserId());

    }
}
