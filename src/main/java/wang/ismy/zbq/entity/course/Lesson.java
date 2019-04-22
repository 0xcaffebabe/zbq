package wang.ismy.zbq.entity.course;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Lesson {

    private Integer lessonId;

    private Integer courseId;

    private String lessonName;

    private String lessonContent;

    private Integer weight;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
