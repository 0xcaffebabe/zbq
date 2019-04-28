package wang.ismy.zbq.model.vo.message;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.UserInfo;

import java.time.LocalDateTime;


/**
 * @author my
 */
@Data
public class UnreadMessageVO {

    private Integer fromUserId;

    private UserInfo fromUserInfo;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;

}
