package wang.ismy.zbq.model.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {

    private Integer lessonId;

    private Integer courseId;

    private String lessonName;

    private String lessonContent;

    private Integer weight;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
