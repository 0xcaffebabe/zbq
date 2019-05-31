package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.system.EmailService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EmailService emailService;


    @Test
    public void testSend() throws MessagingException {
        emailService.sendHtmlMail("715711877@qq.com","邮箱绑定确认",
                "您在转笔圈绑定的邮箱地址，现正在确认，请<a href ='http://baidu.com' style='font-size:36px'>点击我</a>完成绑定");
    }

    @Test
    public void testSendTemplate() throws MessagingException, IOException, TemplateException {
        Map<String,Object> model = new HashMap<>();
        model.put("username","My、");
        model.put("time",LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        model.put("state","我的一条动态");
        emailService.sendTemplateMail("email/likeInform.html","715711877@qq.com","转笔圈通知",model);
    }
}