package wang.ismy.zbq.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.entity.UserInfo;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageVO {

    private Integer senderId;

    private UserInfo senderInfo;

    private String content;

    private LocalDateTime sendTime;
}
