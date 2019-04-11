package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;

@Service
public class InformService {

    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    public InformService() {
        init();
    }

    private void init(){
        new Thread(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if( userService.selectByUsername("10000") == null){
                //TODO
            }

        }).start();
    }

    /*
    * 创建系统通知账号与某个用户的好友关系
    */
    @Transactional
    public void createRelationWithSystemAccount(Integer userId){
        if (friendService.insertNewRelation(userId,0) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
        if (friendService.insertNewRelation(0,userId) != 1){
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }

    public boolean informUser(Integer userId,String info){
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(userId);
        messageDTO.setContent(info);
        User t  = new User();
        t.setUserId(0);
        return messageService.sendMessage(t,messageDTO);
    }
}
