package wang.ismy.zbq.model.dto.course;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author my
 */
@Data
public class LessonCommentDTO {

    @NotNull(message = "章节ID不得为空")
    private Integer lessonId;

    private Integer toUser;

    @NotBlank(message = "评论内容不得为空")
    private String content;

}
