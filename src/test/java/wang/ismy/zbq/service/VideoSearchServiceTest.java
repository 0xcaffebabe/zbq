package wang.ismy.zbq.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.service.video.search.VideoSearchService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoSearchServiceTest {

    @Autowired
    VideoSearchService videoSearchService;


    @Test
    public void testB(){




    }


    @Test
    public void testEngine(){
        var list = videoSearchService.selectAllEngine();

        Assert.assertEquals(4,list.size());
    }


    @Test
    public void getEntry(){

        var map = videoSearchService.selectHotKw();

        System.out.println(map);
    }
}