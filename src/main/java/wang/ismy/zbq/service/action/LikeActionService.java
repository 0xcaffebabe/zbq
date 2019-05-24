package wang.ismy.zbq.service.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.model.entity.like.Like;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.ContentService;
import wang.ismy.zbq.service.LikeService;
import wang.ismy.zbq.service.StateService;
import wang.ismy.zbq.service.user.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
public class LikeActionService {

    @Autowired
    private ContentService contentService;

    @Autowired
    private StateService stateService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    public List<Action> pullLikeAction(User user, Page page) {
        var likeList = likeService.select(user.getUserId(), page);
        List<Integer> contentIdList = new ArrayList<>();
        List<Integer> stateIdList = new ArrayList<>();
        likeList.forEach(e -> {
            switch (LikeTypeEnum.valueOf(e.getLikeType())) {
                case STATE:
                    stateIdList.add(e.getContentId());
                    break;
                case CONTENT:
                    contentIdList.add(e.getContentId());
                    break;
                default:
                    log.info("未知点赞类型:{}", e);

            }
        });


        // 拉取Content
        var contentList = contentService.selectBatch(contentIdList);

        // 拉取State
        var stateList = stateService.selectBatch(stateIdList);

        // 统统转换成Action
        List<Action> actionList = new ArrayList<>();
        contentList.forEach(e->{
            // 根据内容ID获取点赞实体
            var like = getLikeByTypeAndTopic(likeList,LikeTypeEnum.CONTENT,e.getContentId());
            var action = ActionService.generateAction(ActionTypeEnum.LIKE,
                    user,
                    e.getContentId(),
                    "点赞了\""+e.getTitle()+"\"",
                    null,
                    like.getCreateTime());
            actionList.add(action);
        });

        var userMap = UserService.userList2UserVOMap(
                userService.selectByUserIdBatch(stateList.stream()
                        .map(e->e.getUser().getUserId())
                        .collect(Collectors.toList()))
        );

        stateList.forEach(e->{
            // 根据内容ID获取点赞实体
            var like = getLikeByTypeAndTopic(likeList,LikeTypeEnum.STATE,e.getStateId());
            var action = ActionService.generateAction(ActionTypeEnum.LIKE,
                    user,
                    e.getStateId(),
                    "点赞了\""+userMap.get(e.getUser().getUserId()).getNickName()+"的动态\"",
                    e.getContent(),
                    like.getCreateTime());
            actionList.add(action);
        });

        return actionList.stream().sorted(Comparator.comparing(Action::getCreateTime)).collect(Collectors.toList());
    }

    private Like getLikeByTypeAndTopic(List<Like> likeList,LikeTypeEnum typeEnum,Integer topicId){

        for (var i : likeList){
            if (i.getLikeType() == typeEnum.getCode()){
                if (i.getContentId().equals(topicId)){
                    return i;
                }
            }
        }
        return null;
    }
}
