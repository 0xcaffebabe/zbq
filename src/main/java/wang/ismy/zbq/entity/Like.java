package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {

    private Integer likeId;

    private Integer likeType;

    private Integer contentId;

    private User likeUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
