package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.user.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StateServiceTest {

    @Autowired
    StateService stateService;

    @Autowired
    UserService userService;



    @Test
    public void testDelete(){

        userService.setTestUser(userService.selectByPrimaryKey(1));

        stateService.deleteCurrentUserStateById(17);
    }
}