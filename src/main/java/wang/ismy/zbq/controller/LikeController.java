package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.service.LikeService;
import wang.ismy.zbq.service.UserService;
import wang.ismy.zbq.util.ErrorUtils;

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

    @PutMapping("/content/{contentId}")
    @ResultTarget
    @MustLogin
    public Object likeContent(@PathVariable("contentId") Integer contentId){
        if (likeService.createLikeRecord(LikeTypeEnum.CONTENT,contentId,userService.getCurrentUser()) != 1){
            return StringResources.LIKE_FAIL;
        }
        return StringResources.LIKE_SUCCESS;
    }

    @DeleteMapping("/content/{contentId}")
    @ResultTarget
    @MustLogin
    public Object disLikeContent(@PathVariable("contentId") Integer contentId){
        if (likeService.removeLikeRecord(LikeTypeEnum.CONTENT,contentId,userService.getCurrentUser()) != 1){
            ErrorUtils.error(StringResources.DISLIKE_FAIL);
        }
        return StringResources.DISLIKE_SUCCESS;
    }

    @DeleteMapping("/state/{stateId}")
    @ResultTarget
    @MustLogin
    public Object dislikeState(@PathVariable("stateId") Integer stateId){
        if (likeService.removeLikeRecord(LikeTypeEnum.STATE,stateId,userService.getCurrentUser()) !=1){
            ErrorUtils.error(StringResources.DISLIKE_FAIL);
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
