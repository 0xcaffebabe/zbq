package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dto.LocationDTO;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    @Autowired
    LocationService locationService;


    @Autowired
    UserService userService;

    @Test
    public void testSelectAll(){

        var list = locationService.selectAll();

        assertEquals(3,list.size());

        assertNull(list.get(2).getUserVO());
    }


    @Test
    public void testUpdate(){

        userService.setTestUser(userService.selectByPrimaryKey(1));

        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLongitude(new BigDecimal(118));
        locationDTO.setLatitude(new BigDecimal(25));
        locationDTO.setAddress("ceshidizhi");
        locationDTO.setAnonymous(false);

        assertEquals(1,locationService.updateCurrentUserLocation(locationDTO));
    }
}