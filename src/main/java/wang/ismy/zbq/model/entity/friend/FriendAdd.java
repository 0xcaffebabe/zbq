package wang.ismy.zbq.model.entity.friend;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

@Data
public class FriendAdd {

    private Integer friendAddId;

    private User fromUser;

    private User toUser;

    private String msg;

    private Boolean visible;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
