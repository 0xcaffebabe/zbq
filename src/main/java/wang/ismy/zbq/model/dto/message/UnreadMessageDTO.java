package wang.ismy.zbq.model.dto.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class UnreadMessageDTO {

    private Integer fromUser;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;

}
