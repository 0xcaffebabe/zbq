package wang.ismy.zbq.model.dto.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author my
 */
@Data
public class CourseRatingDTO {

    @NotNull(message = "课程ID不得为空")
    private Integer courseId;

    @NotNull(message = "评分不得为空")
    private BigDecimal rating;

    @NotBlank(message = "评论内容不得为空")
    private String content;
}
