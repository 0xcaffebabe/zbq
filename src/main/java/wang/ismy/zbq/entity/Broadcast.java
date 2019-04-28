package wang.ismy.zbq.entity;

import lombok.Data;
import wang.ismy.zbq.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author admin
 */
@Data
public class Broadcast {

    private Integer broadcastId;

    private User user;

    private String content;

    private Boolean anonymous;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
