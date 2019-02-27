package wang.ismy.zbq.controller;

import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.service.FriendService;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/self")
    @ResultTarget
    @MustLogin
    public Object getAllFriends(){
        return friendService.selectCurrentUserAllFriend();
    }

    @GetMapping("/recommend")
    @ResultTarget
    @MustLogin
    public Object getRecommendFriend(){
        return friendService.selectCurrentUserRecommendFriend();
    }
}
