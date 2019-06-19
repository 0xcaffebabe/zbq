package wang.ismy.zbq.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;

/**
 * @author my
 */
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
