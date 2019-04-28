package wang.ismy.zbq.vo.message;

import lombok.Data;
import wang.ismy.zbq.entity.user.UserInfo;

import java.time.LocalDateTime;


/**
 * @author my
 */
@Data
public class MessageVO {

    private Integer senderId;

    private UserInfo senderInfo;

    private String content;

    private LocalDateTime sendTime;

}
