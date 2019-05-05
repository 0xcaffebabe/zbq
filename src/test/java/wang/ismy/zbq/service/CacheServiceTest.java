package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.user.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheServiceTest {

    @Autowired
    private CacheService cacheService;


    @Autowired
    private UserService userService;



    @Test
    public void testIncrease() throws InterruptedException {

        cacheService.hIncrement("test_hash","kw",1L);

        for (int i =0;i<10;i++){
            cacheService.hIncrement("test_hash","kw",1L);
        }

        Thread.sleep(5000);
    }


    @Test
    public void getEntry(){


    }
}