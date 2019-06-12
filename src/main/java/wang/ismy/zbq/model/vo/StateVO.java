package wang.ismy.zbq.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.zbq.model.entity.like.Likes;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author my
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateVO {

    private Integer stateId;

    private String content;

    private UserVO userVO;

    private Likes likes;

    private Boolean self;

    private List<CommentVO> comments = new ArrayList<>();

    private LocalDateTime createTime;

}
