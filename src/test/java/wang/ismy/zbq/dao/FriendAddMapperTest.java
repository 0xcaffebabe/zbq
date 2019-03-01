package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendAddMapperTest {

    @Autowired
    FriendAddMapper friendAddMapper;


    @Test
    public void testSelect(){

        var list = friendAddMapper.selectFriendAddListByToUserId(8);

        assertEquals("你好，交个朋友",list.get(0).getMsg());
    }
}