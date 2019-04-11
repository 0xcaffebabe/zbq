package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonServiceTest {

    @Autowired
    LessonService lessonService;


    @Test
    public void testCountBatch(){

        var map = lessonService.countLessonInBatch(List.of(1,2,3));

        System.out.println(map);
    }
}