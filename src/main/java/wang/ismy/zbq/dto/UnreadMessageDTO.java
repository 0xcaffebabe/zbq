package wang.ismy.zbq.dto;

import lombok.Data;

@Data
public class UnreadMessageDTO {

    private Integer fromUser;

    private Integer msgCount;

    private String newestMsg;
}
