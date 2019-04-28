package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.entity.Message;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageMapperTest {

    @Autowired
    MessageMapper messageMapper;


    @Test
    public void testSelect(){

        List<Message> messages = messageMapper.selectMessageListBy2User(1,2);

        assertEquals(2,messages.size());

        assertEquals("你好，我是root",messages.get(0).getContent());
    }


    @Test
    public void testSelectUnread(){
        var list = messageMapper.selectUnreadMessageByUserId(1);

        assertNotNull(list.get(0).getSendTime());
    }


    @Test
    public void testMessageList(){
        var list = messageMapper.selectMessageListByUserId(1);

        System.out.println(list);


    }
}