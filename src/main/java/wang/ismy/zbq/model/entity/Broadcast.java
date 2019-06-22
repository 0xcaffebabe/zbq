package wang.ismy.zbq.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Broadcast {

    private Integer broadcastId;

    private User user;

    private String content;

    private Boolean anonymous;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
