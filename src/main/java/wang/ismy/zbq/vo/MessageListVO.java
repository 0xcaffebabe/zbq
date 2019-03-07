package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.UserInfo;

@Data
public class MessageListVO {


    private Integer oppositeSideId;

    private UserInfo oppositeSideUserInfo;

    private Integer msgCount;

    private String newestMsg;

}
