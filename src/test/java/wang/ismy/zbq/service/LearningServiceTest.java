package wang.ismy.zbq.service;

import org.aspectj.weaver.ast.Var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LearningServiceTest {

    @Autowired
    LearningService learningService;

    @Autowired
    UserService userService;
    @Test
    public void testState(){
        var map = learningService.selectLearningStateByUserIdAndLessonIdList(2,1, List.of(1,2,3,4));

        assertEquals(true,map.get(1));

        assertEquals(4,map.entrySet().size());
    }


    @Test
    public void testCourseCount(){

        var map = learningService.selectLearningNumberByCourseIdList(List.of(1,2,3));

        assertEquals(3,map.entrySet().size());

        assertEquals(Long.valueOf(2),map.get(1));
    }


    @Test
    public void testLearningCount(){
        System.out.println(learningService.countLearningByUserIdAndCourseIdList(2,List.of(1,2,3)));
    }


    @Test
    public void testProgressBatch(){
        userService.setTestUser(userService.selectByPrimaryKey(2));
        System.out.println(learningService.calcCurrentUserLearningProgressInBatch(List.of(1,2,3)));
    }


    @Test
    public void testLearningList(){
        userService.setTestUser(userService.selectByPrimaryKey(2));
        var list = learningService.selectCurrentUserLearningList();

        assertEquals(2,list.size());

        assertEquals("转笔探讨新手入门篇",list.get(0).getCourseName());

        assertEquals("转笔探讨5",list.get(1).getCourseName());
    }
}