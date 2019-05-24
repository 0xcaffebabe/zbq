package wang.ismy.zbq.service.course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.dto.course.CourseRatingDTO;
import wang.ismy.zbq.service.user.UserService;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseRatingServiceTest {

    @Autowired
    CourseRatingService courseRatingService;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){

        userService.setCurrentUser(userService.selectByPrimaryKey(2));

        CourseRatingDTO dto = new CourseRatingDTO();
        dto.setContent("好垃圾，教都教不好");
        dto.setRating(BigDecimal.valueOf(3.5));
        dto.setCourseId(1);

        courseRatingService.createCurrentUserRating(dto);
    }
}