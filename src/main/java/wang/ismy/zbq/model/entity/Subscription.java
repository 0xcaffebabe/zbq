package wang.ismy.zbq.model.entity;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class Subscription {

    private Integer subscriptionId;

    private User user;

    private User author;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
