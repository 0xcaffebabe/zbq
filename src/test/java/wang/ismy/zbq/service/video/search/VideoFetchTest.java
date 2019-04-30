package wang.ismy.zbq.service.video.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoFetchTest {

    @Autowired
    List<VideoFetch> list;


    @Test
    public void testList(){

        assertEquals(2,list.size());


    }
}