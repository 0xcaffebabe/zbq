package wang.ismy.zbq.model.vo;

import lombok.Data;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.model.vo.user.UserVO;

import java.time.LocalDateTime;

/**
 * @author my
 */
@Data
public class ActionVO {

    private String actionType;

    private UserVO user;

    private Integer topicId;

    private String title;

    private String body;

    private LocalDateTime createTime;

    public static ActionVO convert(Action action){

        ActionVO vo = new ActionVO();
        vo.actionType = action.getActionType().name();
        vo.user = UserVO.convert(action.getUser());
        vo.topicId = action.getSummary().getTopicId();
        vo.title = action.getSummary().getTitle();
        vo.body = action.getSummary().getBody();
        vo.createTime = action.getCreateTime();
        return vo;
    }
}
