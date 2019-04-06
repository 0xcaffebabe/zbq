package wang.ismy.zbq.entity;

import lombok.Data;

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
