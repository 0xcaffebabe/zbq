package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    @Autowired
    LocationService locationService;


    @Test
    public void testSelectAll(){

        var list = locationService.selectAll();

        assertEquals(3,list.size());

        assertNull(list.get(2).getUserVO());
    }
}