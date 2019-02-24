package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.Permission;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionServiceTest {

    @Autowired
    PermissionService permissionService;


    @Test
    public void testInsert(){
        Permission permission = new Permission();
        permission.setContentPublish("N");
        assertEquals(1,permissionService.insertPermission(permission));

        permission = new Permission();
        permission.setContentPublish("Y");

        assertEquals(2,permissionService.insertPermission(permission));
    }
}