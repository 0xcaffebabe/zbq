package wang.ismy.zbq.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LearningVO {

    private Integer courseId;

    private String courseName;

    private String courseImg;

    private BigDecimal learningProgress;

    private Integer lastLessonId;

    private String lastLessonName;

}
