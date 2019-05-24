package wang.ismy.zbq.model.entity.course;

import lombok.Data;
import wang.ismy.zbq.model.UserIdGetter;
import wang.ismy.zbq.model.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class CourseRating implements UserIdGetter {

    private Integer ratingId;

    private Course course;

    private User user;

    /**
     * 1-5 分
     */
    private BigDecimal rating;

    private String content;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    @Override
    public Integer getUserId() {
        return user.getUserId();
    }
}
