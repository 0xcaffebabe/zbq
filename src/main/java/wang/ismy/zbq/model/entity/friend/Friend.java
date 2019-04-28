package wang.ismy.zbq.model.entity.friend;

import lombok.Data;
import wang.ismy.zbq.model.entity.user.UserInfo;


/**
 * @author mt
 */
@Data
public class Friend {

    private Integer friendUserId;

    private UserInfo friendUserInfo;

}
