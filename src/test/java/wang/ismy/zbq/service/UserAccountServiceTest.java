package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.UserAccount;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAccountServiceTest {

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){
        UserAccount account = new UserAccount();
        account.setUser(userService.selectByPrimaryKey(2));
        account.setAccountName("715711877@qq.com");
        account.setAccountType(0);
        account.setValid(false);
        assertEquals(1,userAccountService.createUserAccountRecord(account));
    }
}