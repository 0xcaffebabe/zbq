package wang.ismy.zbq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PutMapping("/{author}")
    @MustLogin
    @ResultTarget
    public Object subscribe(@PathVariable("author") Integer author){
        subscriptionService.currentUserSubscripeAuthor(author);
        return "关注成功";
    }

    @DeleteMapping("/{author}")
    @MustLogin
    @ResultTarget
    public Object unsubscribe(@PathVariable("author") Integer author){
        subscriptionService.currentUserUnsubscribe(author);
        return R.UNSUBSCRIBE_SUCCESS;
    }
}
