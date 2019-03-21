package wang.ismy.zbq.controller.front;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.LikeService;
import wang.ismy.zbq.service.UserService;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @PutMapping("/state/{stateId}")
    @ResultTarget
    @MustLogin
    public Object likeState(@PathVariable("stateId") Integer stateId){
        if (likeService.createLikeRecord(LikeTypeEnum.STATE,stateId,userService.getCurrentUser()) !=1){
            return StringResources.LIKE_FAIL;
        }
        return StringResources.LIKE_SUCCESS;
    }

    @DeleteMapping("/state/{stateId}")
    @ResultTarget
    @MustLogin
    public Object dislikeState(@PathVariable("stateId") Integer stateId){
        if (likeService.removeLikeRecord(LikeTypeEnum.STATE,stateId,userService.getCurrentUser()) !=1){
            return StringResources.DISLIKE_FAIL;
        }
        return StringResources.DISLIKE_SUCCESS;
    }

    @GetMapping("/count")
    @ResultTarget
    @MustLogin
    public Object countLike(){
        User currentUser = userService.getCurrentUser();

        return likeService.countLike(currentUser.getUserId());
    }

}
