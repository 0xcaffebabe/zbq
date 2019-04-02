package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.enums.LikeTypeEnum;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTest {

    @Autowired
    LikeService likeService;


    @Test
    public void testCount(){

        System.out.println(likeService.countLike(2));
    }


    @Test
    public void testHasLike(){
        var map = likeService.selectHasLikeByLikeTypeAndContentIdAndUserIdBatch(LikeTypeEnum.CONTENT, List.of(13,14),1);

        System.out.println(map);
    }
}