package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.FriendMapper;
import wang.ismy.zbq.entity.Friend;
import wang.ismy.zbq.entity.User;

import java.util.List;

@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private UserService userService;

    public List<Friend> selectCurrentUserAllFriend(){
        User user = userService.getCurrentUser();
        return friendMapper.selectFriendByUserId(user.getUserId());
    }

    public List<Friend> selectCurrentUserRecommendFriend(){
        User user = userService.getCurrentUser();
        return friendMapper.selectRecommendFriendByUserId(user.getUserId());
    }
}
