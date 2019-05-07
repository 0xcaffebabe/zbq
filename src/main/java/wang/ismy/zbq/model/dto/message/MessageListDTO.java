package wang.ismy.zbq.model.dto.message;

import lombok.Data;

/**
 * @author my
 */
@Data
public class MessageListDTO {

    private Integer fromUser;

    private Integer toUser;

    private String newestMsg;

}
