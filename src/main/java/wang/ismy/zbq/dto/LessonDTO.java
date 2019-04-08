package wang.ismy.zbq.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LessonDTO {

    @NotNull(message = "课程ID不得为空")
    private Integer courseId;

    @NotBlank(message = "章节标题不得为空")
    private String lessonName;

    @NotBlank(message = "章节内容不得为空")
    private String lessonContent;
}
