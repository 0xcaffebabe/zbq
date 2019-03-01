package wang.ismy.zbq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendAdd {

    private Integer friendAddId;

    private User fromUser;

    private User toUser;

    private String msg;

    private Boolean visible;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
