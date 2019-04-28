package wang.ismy.zbq.model.entity.course;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

@Data
public class Learning {

    private Integer learningId;

    private Integer courseId;

    private Integer lessonId;

    private User user;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
