package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageVO {

    private SenderVO sender;

    private String content;

    private LocalDateTime sendTime;
}
