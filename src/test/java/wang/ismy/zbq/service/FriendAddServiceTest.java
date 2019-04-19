package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.friend.FriendAddService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendAddServiceTest {

    @Autowired
    FriendAddService friendAddService;


    @Test
    public void testSelectList(){
        var list = friendAddService.selectFriendAddListByToUser(8);
        assertEquals("root",list.get(0).getFromUser().getUserInfo().getNickName());
    }
}