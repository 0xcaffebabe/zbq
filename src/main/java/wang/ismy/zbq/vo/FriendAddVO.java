package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.user.UserInfo;

import java.time.LocalDateTime;

@Data
public class FriendAddVO {

    private Integer friendAddId;

    private UserInfo userInfo; // 请求添加放用户信息

    private String msg;

    private Boolean visible;

    private LocalDateTime createTime;


}
