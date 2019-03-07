package wang.ismy.zbq.service;

import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;
    @Test
    public void testSelect(){

        userService.setTestUser(userService.selectByPrimaryKey(1));
        var list = messageService.selectCurrentUserMessageList();

        System.out.println(list);
    }
}