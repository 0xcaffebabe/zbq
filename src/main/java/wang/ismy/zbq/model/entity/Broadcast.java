package wang.ismy.zbq.model.entity;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

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
