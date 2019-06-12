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
public class Content {
    private Integer contentId;

    private User user;

    private String title;

    private String content;

    private String tags;

    private Boolean visible;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
