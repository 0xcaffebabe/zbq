package wang.ismy.zbq.service;

import freemarker.template.TemplateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.CommentMapper;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.CommentCountDTO;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.course.Course;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.service.course.CourseService;
import wang.ismy.zbq.service.course.LessonService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("评论服务测试")
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    private CommentMapper mapper;

    @Mock
    private UserService userService;

    @Mock
    private ExecuteService executeService;

    @Mock
    private InformService informService;

    @Mock
    private StateService stateService;

    @Mock
    private ContentService contentService;

    @Mock
    private LessonService lessonService;

    @Mock
    private CourseService courseService;

    @Mock
    private TemplateEngineService templateEngineService;

    @Mock
    private EmailService emailService;

    @Mock
    private UserAccountService userAccountService;


    /**
     * @see CommentService#selectCommentVOList(CommentTypeEnum, Integer, Page)
     */
    @Test
    public void 测试拉取评论视图列表() {

        when(mapper.selectCommentByCommentTypeAndContentIdPaging(anyInt(),
                anyInt(), any(Page.class)))
                .thenReturn(
                        List.of(
                                Comment.builder()
                                        .content("评论1").fromUser(User.convert(1)).toUser(User.convert(2)).build(),
                                Comment.builder()
                                        .content("评论2").fromUser(User.convert(1)).build(),
                                Comment.builder()
                                        .content("评论3").fromUser(User.convert(2)).toUser(User.convert(3)).build()
                        )
                );
        when(userService.selectByUserIdBatch(argThat(list ->
                list.size() == 3 && list.containsAll(List.of(1, 2, 3))
        ))).thenReturn(
                List.of(
                        User.builder().userId(1).build(),
                        User.builder().userId(2).build(),
                        User.builder().userId(3).build()
                )
        );
        var list = commentService.selectCommentVOList(CommentTypeEnum.CONTENT,
                1, Page.of(1, 5));
        assertEquals(3, list.size());
        assertEquals("评论1", list.get(0).getContent());
        assertEquals(1, (int) list.get(0).getFromUser().getUserId());
        assertEquals(2, (int) list.get(0).getToUser().getUserId());
        assertEquals(1, (int) list.get(1).getFromUser().getUserId());
    }

    /**
     * @see CommentService#selectCommentCount(CommentTypeEnum, List)
     */
    @Test
    public void 测试查询评论数() {
        when(mapper.selectCommentCountByCommentTypeAndTopicIdBatch(eq(1), argThat(list ->
                list.size() == 3 && list.containsAll(List.of(1, 2, 3))
        )))
                .thenReturn(
                        List.of(
                                CommentCountDTO.builder().contentId(1).count(2L).build(),
                                CommentCountDTO.builder().contentId(2).count(4L).build(),
                                CommentCountDTO.builder().contentId(3).count(8L).build()
                        )
                );
        var map = commentService.selectCommentCount(CommentTypeEnum.CONTENT, List.of(1, 2, 3));

        assertEquals(8L, (long) map.get(3));
    }

    /**
     * @see CommentService#createNewCommentRecord(Comment)
     */
    @Nested
    class 评论创建测试集 {
        @Test
        public void 测试创建新评论应该失败() {
            Comment comment = new Comment();
            comment.setContent("测试内容");
            comment.setFromUser(User.convert(1));
            comment.setToUser(User.convert(2));
            comment.setTopicId(1);

            assertThrows(RuntimeException.class, () -> {
                commentService.createNewCommentRecord(comment);
            }, R.TARGET_USER_NOT_EXIST);
        }

        @Test
        public void 测试创建新评论应该成功() {
            Comment comment = new Comment();
            comment.setContent("测试内容");
            comment.setFromUser(User.convert(1));
            comment.setToUser(User.convert(2));
            comment.setTopicId(1);

            when(userService.selectByPrimaryKey(eq(2))).thenReturn(User.convert(2));

            commentService.createNewCommentRecord(comment);
            verify(mapper).insertNew(argThat(com ->
                    com.getTopicId().equals(1)
                            && com.getContent().equals("测试内容")
                            && com.getFromUser().getUserId().equals(1)
                            && com.getToUser().getUserId().equals(2)
            ));
        }
    }


    @Nested
    class 用户通知测试集 {

        /**
         * @see CommentService#informUser(Comment, User)
         */
        @Nested
        class 用户通知情况测试集 {

            @Test
            public void 测试动态评论通知() throws Throwable {

                Comment comment = Comment.builder()
                        .commentId(1)
                        .content("测试评论")
                        .fromUser(User.convert(1))
                        .toUser(User.convert(2))
                        .topicId(1)
                        .commentType(CommentTypeEnum.STATE.getCode())
                        .build();

                User commentUser = User.builder()
                        .userId(1)
                        .userInfo(UserInfo.builder().nickName("用户1").build())
                        .build();
                when(stateService.select(eq(comment.getTopicId())))
                        .thenReturn(
                                State.builder()
                                        .stateId(1)
                                        .user(User.builder()
                                                .userId(1)
                                                .userInfo(UserInfo.builder().nickName("用户1").build())
                                                .build())
                                        .content("测试动态")
                                        .build()
                        );


                var method = commentService.getClass().getDeclaredMethod("informUser", Comment.class, User.class);
                method.setAccessible(true);
                method.invoke(commentService, comment,commentUser);

                assertEquals(comment.getFromUser(), commentUser);
                verify(informService).informUserComment(eq(commentUser),eq(1),argThat(com->com == comment),eq("笔圈动态"),eq("测试动态"));
            }
            @Test
            public void 测试内容评论通知() throws Throwable {

                Comment comment = Comment.builder()
                        .commentId(1)
                        .content("测试评论")
                        .fromUser(User.convert(1))
                        .toUser(User.convert(2))
                        .topicId(1)
                        .commentType(CommentTypeEnum.CONTENT.getCode())
                        .build();

                User commentUser = User.builder()
                        .userId(1)
                        .userInfo(UserInfo.builder().nickName("用户1").build())
                        .build();
                when(contentService.selectRaw(eq(comment.getTopicId())))
                        .thenReturn(
                                Content.builder()
                                        .user(User.convert(1))
                                .content("测试内容")
                                .title("测试标题")
                                .build()
                        );

                var method = commentService.getClass().getDeclaredMethod("informUser", Comment.class, User.class);
                method.setAccessible(true);
                method.invoke(commentService, comment,commentUser);

                assertEquals(comment.getFromUser(), commentUser);
                verify(informService).informUserComment(eq(commentUser),eq(1),argThat(com->com == comment),eq("转笔内容"),eq("测试标题"));
            }

            @Test
            public void 测试课程评论通知() throws Throwable {
                Comment comment = Comment.builder()
                        .commentId(1)
                        .content("测试评论")
                        .fromUser(User.convert(1))
                        .toUser(User.convert(2))
                        .topicId(1)
                        .commentType(CommentTypeEnum.LESSON.getCode())
                        .build();

                User commentUser = User.builder()
                        .userId(1)
                        .userInfo(UserInfo.builder().nickName("用户1").build())
                        .build();
                when(lessonService.selectRawByPrimaryKey(eq(comment.getTopicId())))
                        .thenReturn(
                                Lesson.builder().lessonName("测试章节").courseId(1).build()
                        );
                when(courseService.selectByPrimaryKey(eq(1))).thenReturn(
                        Course.builder().courseName("测试课程")
                                .publisher(User.builder().userId(2).build())
                                .build()
                );

                var method = commentService.getClass().getDeclaredMethod("informUser", Comment.class, User.class);
                method.setAccessible(true);
                method.invoke(commentService, comment,commentUser);

                assertEquals(comment.getFromUser(), commentUser);
                verify(informService).informUserComment(eq(commentUser),eq(2),argThat(com->com == comment),eq("课程"),eq("测试章节"));
            }
        }
    }


}