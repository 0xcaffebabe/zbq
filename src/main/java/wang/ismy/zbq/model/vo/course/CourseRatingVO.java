package wang.ismy.zbq.model.vo.course;

import lombok.Data;
import wang.ismy.zbq.model.entity.course.CourseRating;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class CourseRatingVO {

    private String content;

    private BigDecimal rating;

    private UserVO user;

    private LocalDateTime createTime;

    public static CourseRatingVO convert(CourseRating rating){

        CourseRatingVO vo = new CourseRatingVO();
        vo.content = rating.getContent();
        vo.rating = rating.getRating();
        vo.user = UserVO.convert(rating.getUser());
        vo.createTime = rating.getCreateTime();

        return vo;
    }
}
