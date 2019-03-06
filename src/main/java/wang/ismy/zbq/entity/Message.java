package wang.ismy.zbq.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private Integer messageId;

    private User fromUser;

    private User toUser;

    private String content;

    private Boolean hasRead;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
