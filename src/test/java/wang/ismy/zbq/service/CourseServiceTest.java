package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.service.course.CourseService;
import wang.ismy.zbq.service.user.UserService;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Test
    public void testSelectAll(){

        var list = courseService.selectAll();

        assertEquals(3,list.size());

        assertEquals("转笔探讨2",list.get(0).getCourseName());

        assertEquals("root",list.get(0).getPublisher().getNickName());
    }


    @Test
    public void testSelectSingle(){
        var courseLesson = courseService.selectCourseLessonByCourseId(4);

        assertEquals("转笔探讨5",courseLesson.getCourseName());


    }


    @Test
    public void testSelectComment(){

        userService.setCurrentUser(userService.selectByPrimaryKey(2));
        var list = courseService.selectComment(1,Page.of(1,10));

        assertEquals(2,list.size());

        assertEquals("test2019051317",list.get(0).getContent());

        assertEquals("转笔探讨新手入门篇1 正首环",list.get(0).getLesson().getLessonName());
    }


}