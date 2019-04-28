package wang.ismy.zbq.dao;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dao.user.UserMapper;
import wang.ismy.zbq.model.entity.user.User;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void selectAll() {
        assertEquals(0, userMapper.selectAll().size());
    }


    @Test
    public void testSelectOne() {
        User user = userMapper.selectByUsername("test2");
        assertEquals("MY38", user.getUserInfo().getNickName());
    }


    @Test
    public void testSelectBatch() {

        var list = userMapper.selectByUserIdBatch(Lists.list(8, 6, 5, 9, 59));
        assertEquals(4,list.size());

    }
}