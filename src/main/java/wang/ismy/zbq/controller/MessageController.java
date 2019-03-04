package wang.ismy.zbq.controller;


import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/friend/{id}")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserMessageListByUserId(@PathVariable("id") Integer userId){

        return messageService.selectCurrentUserMessageListByFriendId(userId);
    }
}
