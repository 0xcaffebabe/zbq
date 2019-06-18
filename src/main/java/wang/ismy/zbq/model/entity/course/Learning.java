package wang.ismy.zbq.model.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.entity.user.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Learning {

    private Integer learningId;

    private Integer courseId;

    private Integer lessonId;

    private User user;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
