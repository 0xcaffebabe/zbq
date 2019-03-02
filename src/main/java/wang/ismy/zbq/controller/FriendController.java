package wang.ismy.zbq.controller;

import com.mysql.cj.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.FriendAddDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.FriendAddService;
import wang.ismy.zbq.service.FriendService;
import wang.ismy.zbq.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendAddService friendAddService;


    @GetMapping("/self")
    @ResultTarget
    @MustLogin
    public Object getAllFriends(@RequestParam(value = "kw",required = false) String kw){

        if (StringUtils.isNullOrEmpty(kw)){
            return friendService.selectCurrentUserAllFriend();
        }else{
            return friendService.selectCurrentUserFriendByNickName(kw);
        }

    }

    @GetMapping("/recommend")
    @ResultTarget
    @MustLogin
    public Object getRecommendFriend(@RequestParam(value = "kw",required = false) String kw){

        if (StringUtils.isNullOrEmpty(kw)){
            return friendService.selectCurrentUserRecommendFriend();
        }else{
            return friendService.selectStrangerByNickName(kw);
        }

    }

    @GetMapping("/add")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserFriendAddList(){

        return friendService.selectCurrentUserFriendAddList();
    }

    @PutMapping("/add")
    @ResultTarget
    @MustLogin
    public Object sendFriendAddMsg(@Valid @RequestBody FriendAddDTO dto){

        if (friendService.sendAFriendAddMsg(dto) == 1){
            return StringResources.FRIEND_ADD_MSG_SEND_SUCCESS;
        }else{
            return StringResources.FRIEND_ADD_MSG_SEND_FAIL;
        }
    }


    @PostMapping("/add/agree/{friendAddId}")
    @ResultTarget
    @MustLogin
    public Object agreeFreindAdd(@PathVariable("friendAddId") Integer friendAddId){
        friendAddService.agreeFriendAddMsg(friendAddId);

        return "添加成功！";
    }
}
