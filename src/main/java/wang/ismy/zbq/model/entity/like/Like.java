package wang.ismy.zbq.model.entity.like;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Like {

    private Integer likeId;

    private Integer likeType;

    private Integer contentId;

    private User likeUser;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
