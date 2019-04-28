package wang.ismy.zbq.model.entity.course;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

@Data
public class Course {

    private Integer courseId;

    private String courseName;

    private String courseDesc;

    private String courseImg;

    private User publisher;

    private String courseLevel;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
