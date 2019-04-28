package wang.ismy.zbq.vo.message;

import lombok.Data;
import wang.ismy.zbq.entity.user.UserInfo;


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
