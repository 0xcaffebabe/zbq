package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.Course;
import wang.ismy.zbq.entity.User;

import java.time.LocalDateTime;

@Data
public class CourseVO {

    private Integer courseId;

    private String courseName;

    private String courseDesc;

    private String courseImg;

    private UserVO publisher;

    private String courseLevel;

    private LocalDateTime createTime;

    public static CourseVO convert(Course course){

        CourseVO vo = new CourseVO();
        BeanUtils.copyProperties(course,vo);
        return vo;
    }
}
