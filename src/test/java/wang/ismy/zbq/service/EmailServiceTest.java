package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.system.EmailService;

import javax.mail.MessagingException;


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
}