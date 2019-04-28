package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BroadcastServiceTest {

    @Autowired
    BroadcastService service;


    @Test
    public void test(){
        var list = service.selectNewest10();

        System.out.println(list);
    }
}