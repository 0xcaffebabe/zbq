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
public class State {

    private Integer stateId;

    private String content;

    private User user;

    private Boolean visible;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
