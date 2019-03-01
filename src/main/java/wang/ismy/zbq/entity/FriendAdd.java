package wang.ismy.zbq.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendAdd {

    private Integer friendAddId;

    private Integer fromUser;

    private Integer toUser;

    private String msg;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
