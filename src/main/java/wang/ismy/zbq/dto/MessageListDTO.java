package wang.ismy.zbq.dto;

import lombok.Data;

@Data
public class MessageListDTO {

    private Integer fromUser;

    private Integer toUser;

    private String newestMsg;
}
