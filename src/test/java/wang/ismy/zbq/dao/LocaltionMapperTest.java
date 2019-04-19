package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.Location;
import wang.ismy.zbq.service.user.UserService;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LocaltionMapperTest {

    @Autowired
    LocationMapper locationMapper;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){

        Location location = new Location();
        location.setUser(userService.selectByPrimaryKey(1));
        location.setAddress("福建省泉州市鲤城区媒人桥路2");
        location.setLongitude(BigDecimal.valueOf(118.58));
        location.setLatitude(BigDecimal.valueOf(24.93));
        location.setAnonymous(false);

        assertEquals(1,locationMapper.insertNew(location));

    }
}