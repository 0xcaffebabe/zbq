package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.vo.CommentVO;
import wang.ismy.zbq.model.vo.user.UserVO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.CourseService;
import wang.ismy.zbq.service.course.LessonService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;
import wang.ismy.zbq.util.ErrorUtils;
import wang.ismy.zbq.util.TimeUtils;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author my
 */
@Service
@Slf4j
@Setter(onMethod_ = @Inject)
public class CommentService {

    private CommentMapper mapper;

    private UserService userService;

    private ExecuteService executeService;

    private InformService informService;

    private StateService stateService;

    private ContentService contentService;

    private LessonService lessonService;

    private CourseService courseService;

    private TemplateEngineService templateEngineService;

    private EmailService emailService;

    private UserAccountService userAccountService;

    /**
     * 查询评论列表
     *
     * @param commentType 评论类型
     * @param topicIds    主题ID列表
     * @return 评论列表
     */
    public List<Comment> selectComments(CommentTypeEnum commentType,
                                        List<Integer> topicIds) {
        if (topicIds.size() == 0) {
            return List.of();
        }
        return mapper.selectCommentListByCommentTypeAndTopicIdBatch(commentType.getCode(), topicIds);
    }

    /**
     * 分页查询评论列表
     *
     * @param commentType 评论类型
     * @param topicIds    主题ID列表
     * @return 评论列表
     */
    public List<Comment> selectComments(CommentTypeEnum commentType,
                                        List<Integer> topicIds, Page page) {

        if (topicIds.size() == 0) {
            return List.of();
        }
        return mapper.selectCommentListByCommentTypeAndTopicIdBatchPaging(commentType.getCode(), topicIds, page);
    }

    /**
     * 取出评论列表
     *
     * @param commentType 评论类型
     * @param contentId   内容ID
     * @param page        分页组件
     * @return 评论列表
     */
    public List<Comment> selectComments(CommentTypeEnum commentType,
                                        Integer contentId,
                                        Page page) {
        return mapper.selectCommentByCommentTypeAndContentIdPaging(commentType.getCode(), contentId, page);
    }

    public List<CommentVO> selectCommentVOList(CommentTypeEnum commentType,
                                               Integer contentId,
                                               Page page) {
        var commentList = selectComments(commentType, contentId, page);

        var commentVOList = commentList.stream()
                .map(CommentVO::convert)
                .collect(Collectors.toList());

        List<Integer> userIdList = new ArrayList<>();

        for (var i : commentVOList) {
            if (i.getToUser() != null && !userIdList.contains(i.getToUser().getUserId())) {
                userIdList.add(i.getToUser().getUserId());

            }

            if (!userIdList.contains(i.getFromUser().getUserId())) {
                userIdList.add(i.getFromUser().getUserId());
            }


        }

        var userList = userService.selectByUserIdBatch(userIdList);

        Map<Integer, UserVO> userVOMap = new HashMap<>();
        for (var i : userList) {
            userVOMap.put(i.getUserId(), UserVO.convert(i));
        }

        for (var i : commentVOList) {

            i.setFromUser(userVOMap.get(i.getFromUser().getUserId()));

            if (i.getToUser() != null) {
                i.setToUser(userVOMap.get(i.getToUser().getUserId()));
            }
        }

        return commentVOList;

    }

    /**
     * 查询主题评论数量
     *
     * @param commentType 评论类型
     * @param idList      主题ID列表
     * @return 主题ID映射评论数量哈希表
     */
    public Map<Integer, Long> selectCommentCount(CommentTypeEnum commentType, List<Integer> idList) {

        if (idList == null || idList.size() == 0) {
            return Map.of();
        }
        var list = mapper.selectCommentCountByCommentTypeAndTopicIdBatch(commentType.getCode(), idList);

        Map<Integer, Long> ret = new HashMap<>();
        for (var i : list) {
            ret.put(i.getContentId(), i.getCount());
        }

        return ret;
    }

    /**
     * 新建一条评论记录
     *
     * @param comment 评论实体
     * @return 受影响行数
     */
    public int createNewCommentRecord(Comment comment) {

        if (comment.getToUser() != null) {
            if (userService.selectByPrimaryKey(comment.getToUser().getUserId()) == null) {
                ErrorUtils.error(R.TARGET_USER_NOT_EXIST);
            }
        }

        var currentUser = userService.getCurrentUser();
        executeService.submit(() -> {
            informUser(comment, currentUser);
        });

        return mapper.insertNew(comment);
    }

    private void informUser(Comment comment, User commentUser) {

            switch (CommentTypeEnum.of(comment.getCommentType())) {
                case STATE:

                    State state = stateService.select(comment.getTopicId());

                        informService.informUserComment(commentUser,
                            state.getUser().getUserId(),
                            comment, "笔圈动态",
                            state.getContent().length() >= 15 ?
                                    state.getContent().substring(0, 15) + "..." : state.getContent());

                    break;
                case CONTENT:

                    Content content = contentService.selectRaw(comment.getTopicId());
                    informService.informUserComment(commentUser,
                            content.getUser().getUserId(),
                            comment, "转笔内容",
                            content.getTitle().length() >= 15 ?
                                    content.getTitle().substring(0, 15) + "..." : content.getTitle());
                    break;
                case LESSON:
                    var lesson = lessonService.selectRawByPrimaryKey(comment.getTopicId());

                    if (lesson == null){
                        log.error("没有相关章节:{}",comment.getTopicId());
                        return;
                    }

                    var course = courseService.selectByPrimaryKey(lesson.getCourseId());

                    informService.informUserComment(commentUser,course.getPublisher().getUserId(),
                            comment,"课程",lesson.getLessonName());

                    break;
                default:
            }

    }


    public List<Comment> select(Integer userId, Page page) {

        return mapper.selectCommentByUserPaging(userId, page);
    }
}
