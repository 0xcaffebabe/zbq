package wang.ismy.zbq.service.system;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

/**
 * @author my
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${spring.mail.username}")
    private String from;

    public void sendtTextMail(String to,String subject,String content){
        log.info("发送一封邮件给{}",to);

        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        javaMailSender.send(message);
    }

    public void sendHtmlMail(String to,String subject,String content) throws MessagingException {
        log.info("发送一封邮件给{}",to);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        javaMailSender.send(mimeMessage);
    }

    public void sendHtmlMail(Integer userId,String subject,String content) throws MessagingException {

        var account = userAccountService.selectByAccountTypeAndUserId(UserAccountEnum.EMAIL,userId);

        if (account == null){
            log.warn("取消发送邮件，用户不存在或没有绑定邮箱：{}",userId);
            return;
        }

        sendHtmlMail(account.getAccountName(),subject,content);
    }

    public void sendTemplateMail(String templateName,String to,String subject,Map<String,Object> modelMap) throws MessagingException, IOException, TemplateException {
        log.info("发送一封邮件给{}",to);

        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        String html=FreeMarkerTemplateUtils.processTemplateIntoString(template,modelMap);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html,true);
        javaMailSender.send(mimeMessage);
    }
}
