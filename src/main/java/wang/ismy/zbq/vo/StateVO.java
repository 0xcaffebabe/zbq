package wang.ismy.zbq.vo;

import lombok.Data;
import wang.ismy.zbq.entity.Likes;
import wang.ismy.zbq.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StateVO {

    private Integer stateId;

    private String content;

    private UserVO userVO;

    private Likes likes;

    private Boolean self;

    private List<CommentVO> comments = new ArrayList<>();

    private LocalDateTime createTime;
}
