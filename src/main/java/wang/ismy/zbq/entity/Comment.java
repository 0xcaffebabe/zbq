package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {

    private Integer commentId;

    private Integer commentType;

    private Integer topicId;

    private String content;

    private User fromUser;

    private User toUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
