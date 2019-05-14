package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.dto.course.LessonCommentDTO;
import wang.ismy.zbq.service.course.LessonService;
import wang.ismy.zbq.service.user.UserService;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonServiceTest {

    @Autowired
    LessonService lessonService;

    @Autowired
    UserService userService;

    @Test
    public void testCountBatch(){

        var map = lessonService.countLessonInBatch(List.of(1,2,3));

        System.out.println(map);
    }


    @Test
    public void testINsert(){

        userService.setCurrentUser(userService.selectByPrimaryKey(2));

        LessonCommentDTO dto = new LessonCommentDTO();
        dto.setLessonId(1);
        dto.setContent("测试评论，");
        lessonService.publishLessonComment(dto);
    }
}