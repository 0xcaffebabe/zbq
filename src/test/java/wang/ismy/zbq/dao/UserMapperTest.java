package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.ZbqApplication;
import wang.ismy.zbq.entity.User;

import javax.sql.DataSource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectAll() {
        assertEquals(0,userMapper.selectAll().size());
    }


    @Test
    public void testSelectOne(){
        User user = userMapper.selectByUsername("test2");
        assertEquals("MY38",user.getUserInfo().getNickName());
    }
}