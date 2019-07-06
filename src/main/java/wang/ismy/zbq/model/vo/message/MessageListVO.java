package wang.ismy.zbq.model.vo.message;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.model.vo.user.UserVO;


/**
 * @author my
 */
@Data
public class MessageListVO {

    private Integer oppositeSideId;

    private UserVO oppositeSideUserInfo;

    private Integer msgCount;

    private String newestMsg;


}
