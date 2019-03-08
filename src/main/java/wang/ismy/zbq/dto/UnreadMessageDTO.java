package wang.ismy.zbq.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UnreadMessageDTO {

    private Integer fromUser;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;
}
