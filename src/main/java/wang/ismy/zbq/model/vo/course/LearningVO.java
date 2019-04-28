package wang.ismy.zbq.model.vo.course;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author my
 */
@Data
public class LearningVO {

    private Integer courseId;

    private String courseName;

    private String courseImg;

    private BigDecimal learningProgress;

    private Integer lastLessonId;

    private String lastLessonName;

}
