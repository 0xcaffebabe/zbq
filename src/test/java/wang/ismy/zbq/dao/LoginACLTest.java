package wang.ismy.zbq.dao;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dao.user.LoginACLMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginACLTest {

    @Autowired
    LoginACLMapper mapper;


    @Test
    public void tesSelect(){

        Assert.assertEquals(null,mapper.selectLoginStateByUserId(1));

        Assert.assertEquals(false,mapper.selectLoginStateByUserId(15));
    }
}
