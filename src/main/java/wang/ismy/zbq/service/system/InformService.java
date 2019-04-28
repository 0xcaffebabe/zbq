package wang.ismy.zbq.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.dto.message.MessageDTO;
import wang.ismy.zbq.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.annotation.PostConstruct;

/**
 * @author my
 */
@Service
public class InformService {

    public static final String SYSTEM_ACCOUNT_USERNAME = "10000";
    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExecuteService executeService;


    @PostConstruct
    public void init(){
        executeService.submit(()->{
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if( userService.selectByUsername(SYSTEM_ACCOUNT_USERNAME) == null){
                //TODO
            }

        });
    }

    /**
    * 创建系统通知账号与某个用户的好友关系
     * @param userId 用户ID
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
