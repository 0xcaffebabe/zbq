package wang.ismy.zbq.model.vo.message;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.UserInfo;


/**
 * @author my
 */
@Data
public class MessageListVO {

    private Integer oppositeSideId;

    private UserInfo oppositeSideUserInfo;

    private Integer msgCount;

    private String newestMsg;
}
