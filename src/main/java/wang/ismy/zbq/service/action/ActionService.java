package wang.ismy.zbq.service.action;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;

import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.vo.ActionVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
public class ActionService {

    @Autowired
    private UserService userService;

    @Autowired
    private ExecuteService executeService;

    @Autowired
    private CollectActionService collectActionService;

    @Autowired
    private CommentActionService commentActionService;

    @Autowired
    private LikeActionService likeActionService;

    @Autowired
    private PublishActionService publishActionService;

    public List<ActionVO> pullActions(Integer userId, Page page){
        long time = System.currentTimeMillis();
        var user = userService.selectByPrimaryKey(userId);
        if (user == null){
            ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
        }


        // 拉取评论Action
        var  task1 = executeService.submit(() -> commentActionService.pullActions(user,page));
        // 拉取点赞Action
        var task2 = executeService.submit(() -> likeActionService.pullLikeAction(user,page));
        // 拉取收藏Action
        var task3 = executeService.submit(() -> collectActionService.pullAction(user,page));
        // 拉取发布Action
        var task4 = executeService.submit(() -> publishActionService.pullActions(user,page));

        List<Action> actionList = new ArrayList<>();
        try{
            actionList.addAll(task1.get());
            actionList.addAll(task2.get());
            actionList.addAll(task3.get());
            actionList.addAll(task4.get());
            log.info("获取用户{}最近动态，耗时:{}",userId, System.currentTimeMillis()-time);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return actionList
                .stream()
                .sorted((e1,e2)-> -e1.getCreateTime().compareTo(e2.getCreateTime()))
                .map(ActionVO::convert)
                .collect(Collectors.toList());
    }

    public static Action generateAction(ActionTypeEnum type,
                                 User user,
                                 Integer topicId,
                                 String title,
                                 String body,
                                 LocalDateTime createTime){
        return Action.generateAction(type,user,topicId,title,body,createTime);
    }

}
