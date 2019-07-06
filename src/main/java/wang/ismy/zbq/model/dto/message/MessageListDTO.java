package wang.ismy.zbq.model.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageListDTO {

    private Integer fromUser;

    private Integer toUser;

    private String newestMsg;

}
