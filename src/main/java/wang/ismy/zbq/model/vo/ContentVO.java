package wang.ismy.zbq.model.vo;

import lombok.Data;
import wang.ismy.zbq.model.vo.user.AuthorVO;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class ContentVO {
    private Integer contentId;

    private String title;

    private String content;

    private AuthorVO user;

    private Long likeCount;

    private Boolean hasLike;

    private Long commentCount;

    private Long collectCount;

    private Boolean hasCollect;

    private LocalDateTime createTime;


}
