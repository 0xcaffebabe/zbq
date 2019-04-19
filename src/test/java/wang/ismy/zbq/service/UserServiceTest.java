package wang.ismy.zbq.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dto.RegisterDTO;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.entity.UserPermission;
import wang.ismy.zbq.service.user.UserService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;
    @Test
    public void testRegister() throws NoSuchAlgorithmException {

        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("my1");
        dto.setPassword(new String(MessageDigest.getInstance("md5").digest("123".getBytes())));
        Assert.assertEquals(1,userService.createNewUser(dto));
    }


    @Test
    public void testUpdate(){

        UserPermission userPermission = new UserPermission();
        userPermission.setLogin(true);
        userPermission.setContentPublish(false);

        var userList = userService.selectAll();

        for (var i : userList){
            User u = new User();
            u.setUserId(i.getUserId());
            permissionService.insertPermission(userPermission);
            u.setUserPermission(userPermission);
            userService.update(u);
        }


    }

}
