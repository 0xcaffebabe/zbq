package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class CourseVO {

    private Integer courseId;

    private String courseName;

    private String courseDesc;

    private String courseImg;

    private UserVO publisher;

    private String courseLevel;

    /**
     * 学习人数
     */
    private Long learningNumber;

    /**
     * 当前用户学习进度
     */
    private BigDecimal currentProgress;

    private LocalDateTime createTime;

    public static CourseVO convert(Course course) {

        CourseVO vo = new CourseVO();
        BeanUtils.copyProperties(course, vo);
        return vo;
    }
}
