package wang.ismy.zbq.model.entity.action;

import lombok.Data;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.model.entity.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author my
 */
@Data
public class Action {

    private String actionId;

    private ActionTypeEnum actionType;

    private User user;

    private ActionSummary summary;

    private LocalDateTime createTime;

    public static Action generateAction(ActionTypeEnum type,
                                        User user,
                                        Integer topicId,
                                        String title,
                                        String body,
                                        LocalDateTime createTime){
        Action action = new Action();
        action.setActionType(type);
        action.setActionId(UUID.randomUUID().toString());
        action.setUser(user);
        ActionSummary summary = new ActionSummary();
        summary.setTopicId(topicId);
        summary.setTitle(title);
        summary.setBody(body);
        action.setSummary(summary);
        action.setCreateTime(createTime);
        return action;
    }
}
