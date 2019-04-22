package wang.ismy.zbq.entity.friend;

import lombok.Data;
import wang.ismy.zbq.entity.user.UserInfo;


/**
 * @author mt
 */
@Data
public class Friend {

    private Integer friendUserId;

    private UserInfo friendUserInfo;

}
