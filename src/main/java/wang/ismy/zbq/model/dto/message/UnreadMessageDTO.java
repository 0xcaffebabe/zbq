package wang.ismy.zbq.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnreadMessageDTO {

    private Integer fromUser;

    private Integer msgCount;

    private String newestMsg;

    private LocalDateTime sendTime;

}
