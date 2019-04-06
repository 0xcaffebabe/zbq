package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    CourseService courseService;


    @Test
    public void testSelectAll(){

        var list = courseService.selectAll();

        assertEquals(3,list.size());

        assertEquals("转笔探讨2",list.get(0).getCourseName());

        assertEquals("root",list.get(0).getPublisher().getNickName());
    }
}