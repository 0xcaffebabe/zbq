package wang.ismy.zbq.model.vo.message;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.UserInfo;

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
