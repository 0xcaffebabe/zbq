package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.UserInfo;

@Data
public class UnreadMessageVO {

    private Integer fromUserId;

    private UserInfo fromUserInfo;

    private Integer msgCount;

    private String newestMsg;
}
