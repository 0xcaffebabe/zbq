package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Lesson {

    private Integer lessonId;

    private Integer courseId;

    private String lessonName;

    private String lessonContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
