package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendServiceTest {

    @Autowired
    FriendService friendService;


    @Test
    public void testSelectFriendId(){
        var list = friendService.selectFriendListByUserId(1);
        System.out.println(list);
    }
}