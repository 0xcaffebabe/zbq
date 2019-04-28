package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.vo.user.UserVO;

import java.time.LocalDateTime;

@Data
public class ContentVO {
    private Integer contentId;

    private String title;

    private String content;

    private UserVO user;

    private Long likeCount;

    private Boolean hasLike;

    private Long commentCount;

    private Integer collectCount;

    private LocalDateTime createTime;


}
