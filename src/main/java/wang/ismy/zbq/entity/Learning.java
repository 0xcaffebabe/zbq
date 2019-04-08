package wang.ismy.zbq.entity;

import lombok.Data;

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
