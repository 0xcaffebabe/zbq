package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.vo.CommentVO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService commentService;


    @Test
    public void testSelect(){

    }


    @Test
    public void testCount(){

        var map = commentService.selectCommentCountByCommentTypeAndTopicIdBatch(CommentTypeEnum.CONTENT,List.of(13,14));

        for (int i = 0; i < 100; i++) {
            assertEquals(null,map);
        }

    }

}