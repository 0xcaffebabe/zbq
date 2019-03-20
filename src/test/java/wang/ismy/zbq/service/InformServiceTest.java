package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class InformServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private InformService informService;

    @Test
    public void tmpExe(){

        var userList = userService.selectAll();


        int count =0;
        for (var user: userList){
            if (!friendService.isFriend(user.getUserId(),0)){
                informService.createRelationWithSystemAccount(user.getUserId());
                count++;
            }
        }
        System.out.println("完成，修改关系数："+count);


    }
}