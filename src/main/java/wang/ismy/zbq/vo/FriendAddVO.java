package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.user.UserInfo;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class FriendAddVO {

    private Integer friendAddId;

    private Integer userId;

    /**
     * 请求添加放用户信息
     */
    private UserInfo userInfo;

    private String msg;

    private Boolean visible;

    private LocalDateTime createTime;


}
