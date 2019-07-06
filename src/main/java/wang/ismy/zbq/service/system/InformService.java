package wang.ismy.zbq.service.system;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.zbq.enums.InformTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.SubscriptionService;
import wang.ismy.zbq.service.TemplateEngineService;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.util.TimeUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

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

    @Setter(onMethod_ =@Inject)
    private MessageService messageService;

    @Setter(onMethod_ =@Inject)
    private FriendService friendService;

    @Setter(onMethod_ =@Inject)
    private UserService userService;

    @Setter(onMethod_ =@Inject)
    private ExecuteService executeService;

    @Setter(onMethod_ =@Inject)
    private EmailService emailService;

    @Setter(onMethod_ =@Inject)
    private UserAccountService userAccountService;

    @Setter(onMethod_ =@Inject)
    private TemplateEngineService templateEngineService;

    @Setter(onMethod_ =@Inject)
    private SubscriptionService subscriptionService;

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

    public void informUserComment(User commentUser, Integer authorUserId,
                                   Comment comment, String type, String content) {

        var author = userService.selectByPrimaryKey(authorUserId);
        if (author == null) {
            log.error("评论通知用户不存在:{}", authorUserId);
            return;
        }

        String commentContent = comment.getContent().length() >= 15 ?
                comment.getContent().substring(0, 15) + "..." : comment.getContent();
        String nickName = commentUser.getUserInfo().getNickName();

        Map<String, Object> modelMap = Map.of("user", nickName,
                "time", TimeUtils.getStrTime(),
                "type", type,
                "comment", commentContent,
                "content", content);
        // 系统通知用户
        String msg = templateEngineService.parseStr(TemplateEngineService.COMMENT_TEMPLATE, modelMap);
        informUser(authorUserId, msg);

        // 邮箱通知用户

        try {
            String html = templateEngineService.parseModel("email/commentInform.html",
                    Map.of("user", nickName,
                            "time", TimeUtils.getStrTime(),
                            "type", type,
                            "comment", commentContent,
                            "content", content));
            emailService.sendHtmlMail(authorUserId, "【转笔圈】评论通知", html);
        } catch (IOException | TemplateException | MessagingException e) {
            log.error("发送邮件时发生异常：{}", e.getMessage());
        }

    }

    public void informUserContent(Integer authorUserId,String content){
        var author = userService.selectByPrimaryKey(authorUserId);
        if (author == null) {
            log.error("评论通知用户不存在:{}", authorUserId);
            return;
        }

        Map<String, Object> modelMap = Map.of("user", author.getUserInfo().getNickName(),
                "time", TimeUtils.getStrTime(),
                "content", content);

        var subscripters = subscriptionService.selectSubscriperAll(authorUserId);

        // 系统通知用户
        String msg = templateEngineService.parseStr(TemplateEngineService.CONTENT_TEMPLATE, modelMap);

        executeService.submit(()->{
            log.info("正在给{}位用户推送消息通知",subscripters.size());
            for (var i : subscripters){
                informUser(i, msg);
            }

        });


        // 邮箱通知用户
        try {
            String html = templateEngineService.parseModel("email/contentInform.html",
                    modelMap);
            executeService.submit(()->{{
                log.info("正在给{}位用户推送邮件通知",subscripters.size());
                for (var i : subscripters){
                    try {
                        emailService.sendHtmlMail(i, "【转笔圈】你关注的人发布新内容了", html);
                    } catch (MessagingException e) {
                        log.error("发送邮件时发生异常：{}", e.getMessage());
                    }
                }

            }});

        } catch (IOException | TemplateException e) {
            log.error("模板发生异常：{}", e.getMessage());
        }
    }

}
