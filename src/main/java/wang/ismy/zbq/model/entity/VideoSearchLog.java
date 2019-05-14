package wang.ismy.zbq.model.entity;


import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class VideoSearchLog {

    private Integer logId;

    private User user;

    private String kw;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
