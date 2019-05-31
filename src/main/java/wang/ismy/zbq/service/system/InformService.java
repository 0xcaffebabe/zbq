package wang.ismy.zbq.service.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.enums.InformTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;

/**
 * 消息通知服务，此接口负责给用户下发消息
 * 此接口只负责通过系统通知账号给用户下发消息
 *
 * @author my
 */
@Service
@Slf4j
public class InformService {

    private static final String SYSTEM_ACCOUNT_USERNAME = "10000";

    @Autowired
    private MessageService messageService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExecuteService executeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserAccountService userAccountService;

    @PostConstruct
    public void init() {
        executeService.submit(() -> {
            // 找不到系统通知账号
            if (userService.selectByUsername(SYSTEM_ACCOUNT_USERNAME) == null) {

                log.error("找不到系统通知账号，请手动运行init.sql创建");
            }

        });
    }

    /**
     * 创建系统通知账号与某个用户的好友关系
     *
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    public void createRelationWithSystemAccount(Integer userId) {
        if (friendService.insertNewRelation(userId, 0) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }
        if (friendService.insertNewRelation(0, userId) != 1) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

    }



    /**
     * 该函数为非阻塞操作，调用后马上返回，发送线程会交给调度器
     *
     * @param userId 用户ID
     * @param info   通知内容
     */
    public void informUser(Integer userId, String info) {

        executeService.submit(() -> {
            var user = userService.selectByPrimaryKey(userId);

            if (user == null) {
                log.warn("通知用户不存在:{}", userId);
                return;
            }

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setTo(userId);
            messageDTO.setContent(info);
            User t = new User();
            t.setUserId(0);
            messageService.sendMessage(t, messageDTO);
        });
    }
}
