package wang.ismy.zbq.service.action;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.enums.ActionTypeEnum;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.CommentService;
import wang.ismy.zbq.service.ContentService;
import wang.ismy.zbq.service.StateService;
import wang.ismy.zbq.service.course.LessonService;
import wang.ismy.zbq.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
public class CommentActionService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private StateService stateService;

    @Autowired
    private UserService userService;

    @Autowired
    private Gson gson;

    public List<Action> pullActions(User user, Page page) {

        var commentList = commentService.select(user.getUserId(), page);

        List<Integer> contentIdList = new ArrayList<>();
        List<Integer> lessonIdList = new ArrayList<>();
        List<Integer> stateIdList = new ArrayList<>();

        commentList.forEach(e -> {

            switch (CommentTypeEnum.of(e.getCommentType())) {
                case CONTENT:
                    contentIdList.add(e.getTopicId());
                    break;
                case LESSON:
                    lessonIdList.add(e.getTopicId());
                    break;
                case STATE:
                    stateIdList.add(e.getTopicId());
                    break;
                default:
                    log.info("未知评论类型：{}", e);

            }
        });

        List<Action> actionList = new ArrayList<>();
        // 拉取内容+评论
        var contentList = contentService.selectBatch(contentIdList);
        contentList.forEach(e -> {

            var comments = getCommentByTypeAndTopic(commentList, CommentTypeEnum.CONTENT, e.getContentId());

            for (var i : comments) {
                var action = ActionService
                        .generateAction(ActionTypeEnum.COMMENT,
                                user,
                                e.getContentId(),
                                "评论了\""+e.getTitle()+"\"",
                                i.getContent(),
                                i.getCreateTime());
                actionList.add(action);
            }

        });
        // 拉取章节+评论
        var lessonList = lessonService.selectBatch(lessonIdList);


        lessonList.forEach(e -> {
            var comments = getCommentByTypeAndTopic(commentList, CommentTypeEnum.LESSON, e.getLessonId());
            for (var i : comments) {
                var action = ActionService
                        .generateAction(ActionTypeEnum.COMMENT,
                                user,
                                e.getLessonId(),
                                "评论了\""+e.getLessonName()+"\"",
                                i.getContent(),
                                i.getCreateTime());
                actionList.add(action);
            }
        });
        // 拉取动态+评论
        var stateList = stateService.selectBatch(stateIdList);

        var userMap = UserService.userList2UserVOMap(
            userService.selectByUserIdBatch(stateList.stream()
                    .map(e->e.getUser().getUserId())
                    .collect(Collectors.toList()))
        );

        stateList.forEach(e -> {


            var comments = getCommentByTypeAndTopic(commentList, CommentTypeEnum.STATE, e.getStateId());

            for (var i : comments) {
                var action = ActionService
                        .generateAction(ActionTypeEnum.COMMENT,
                                user,
                                e.getStateId(),
                                "评论了\""+userMap.get(e.getUser().getUserId()).getNickName()+"的动态"+"\"",
                                i.getContent(),
                                i.getCreateTime());
                actionList.add(action);
            }


        });
        return actionList;
    }

    private List<Comment> getCommentByTypeAndTopic(List<Comment> commentList, CommentTypeEnum type, Integer topicId) {
        List<Comment> ret = new ArrayList<>();
        for (var i : commentList) {
            if (i.getCommentType() == type.getCode()) {
                if (i.getTopicId().equals(topicId)) {
                    ret.add(i);
                }
            }
        }
        return ret;
    }
}
