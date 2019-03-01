package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.entity.UserInfo;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendAddVO {

    private Integer friendAddId;

    private UserInfo userInfo; // 请求添加放用户信息

    private String msg;

    private LocalDateTime createTime;
}
