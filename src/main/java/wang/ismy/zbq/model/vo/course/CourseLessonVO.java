package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.course.Course;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseLessonVO {

    private Integer courseId;

    private String courseName;

    private String courseImg;

    private String courseLevel;

    private String courseDesc;

    private List<LessonListVO> lessonList = new ArrayList<>();

    public static CourseLessonVO convert(Course course){
        CourseLessonVO vo = new CourseLessonVO();
        BeanUtils.copyProperties(course,vo);
        return vo;
    }
}
