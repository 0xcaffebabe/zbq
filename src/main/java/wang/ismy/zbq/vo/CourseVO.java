package wang.ismy.zbq.vo;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.entity.course.Course;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseVO {

    private Integer courseId;

    private String courseName;

    private String courseDesc;

    private String courseImg;

    private UserVO publisher;

    private String courseLevel;

    private Long learningNumber; // 学习人数

    private BigDecimal currentProgress; // 当前用户学习进度

    private LocalDateTime createTime;

    public static CourseVO convert(Course course){

        CourseVO vo = new CourseVO();
        BeanUtils.copyProperties(course,vo);
        return vo;
    }
}
