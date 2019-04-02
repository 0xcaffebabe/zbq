package wang.ismy.zbq.controller;


import org.apache.ibatis.annotations.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import wang.ismy.zbq.annotations.MustLogin;
import wang.ismy.zbq.annotations.ResultTarget;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.service.MessageService;

import javax.validation.Valid;

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

    @PostMapping("")
    @ResultTarget
    @MustLogin
    public Object sendMessage(@RequestBody @Valid MessageDTO messageDTO){

        if (messageService.currentUserSendMessage(messageDTO)){
            return "发送成功";
        }else{
            return "发送失败";
        }
    }

    @GetMapping("/unread")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserUnreadMessage(){
        return messageService.selectCurrentUserUnreadMessageList();
    }

    @GetMapping("/list")
    @ResultTarget
    @MustLogin
    public Object getCurrentUserMessageList(){
        return messageService.selectCurrentUserMessageList();
    }


}
