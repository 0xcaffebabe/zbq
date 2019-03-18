package wang.ismy.zbq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
