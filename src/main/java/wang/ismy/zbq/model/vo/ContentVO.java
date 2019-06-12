package wang.ismy.zbq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.vo.user.AuthorVO;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentVO  {

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
