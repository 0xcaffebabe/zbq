package wang.ismy.zbq.service.action;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.enums.CommentTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.Content;
import wang.ismy.zbq.model.entity.State;
import wang.ismy.zbq.model.entity.course.Lesson;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.CommentService;
import wang.ismy.zbq.service.ContentService;
import wang.ismy.zbq.service.StateService;
import wang.ismy.zbq.service.course.LessonService;
import wang.ismy.zbq.service.user.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("评论动态服务")
class CommentActionServiceTest {

    @Mock
    CommentService commentService;

    @Mock
    ContentService contentService;

    @Mock
    LessonService lessonService;

    @Mock
    StateService stateService;

    @Mock
    UserService userService;

    @InjectMocks
    CommentActionService commentActionService;


    @Test
    public void 测试拉取评论动态() {
        var user = User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build();

        when(commentService.select(eq(1), any(Page.class))).thenReturn(
                List.of(
                        Comment.builder().commentId(1)
                                .commentType(CommentTypeEnum.STATE.getCode()).content("动态评论1").topicId(1).build(),
                        Comment.builder().commentId(2)
                                .commentType(CommentTypeEnum.STATE.getCode()).content("动态评论2").topicId(2).build(),
                        Comment.builder().commentId(3)
                                .commentType(CommentTypeEnum.CONTENT.getCode()).content("内容评论1").topicId(1).build(),
                        Comment.builder().commentId(4)
                                .commentType(CommentTypeEnum.CONTENT.getCode()).content("内容评论2").topicId(2).build(),
                        Comment.builder().commentId(5)
                                .commentType(CommentTypeEnum.LESSON.getCode()).content("章节评论1").topicId(1).build(),
                        Comment.builder().commentId(6)
                                .commentType(CommentTypeEnum.LESSON.getCode()).content("章节评论2").topicId(2).build()
                )
        );

        when(contentService.selectBatch(argThat(l ->
                l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        List.of(
                                Content.builder().contentId(1).title("内容1标题").content("内容1").build(),
                                Content.builder().contentId(2).title("内容2标题").content("内容2").build()
                        )
                );


        when(lessonService.selectBatch(argThat(l ->
                l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        List.of(
                                Lesson.builder().lessonId(1).lessonName("章节1").build(),
                                Lesson.builder().lessonId(2).lessonName("章节2").build()
                        )
                );

        when(stateService.selectBatch(argThat(l ->
                l.containsAll(List.of(1, 2))
        )))
                .thenReturn(
                        List.of(
                                State.builder().stateId(1).content("动态1").user(User.convert(1)).build(),
                                State.builder().stateId(2).content("动态2").user(User.convert(2)).build()
                        )
                );

        when(userService.selectByUserIdBatch(argThat(l ->
                l.containsAll(List.of(1, 2))
        ))).thenReturn(
                List.of(
                        User.builder().userId(1)
                                .userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                        User.builder().userId(2)
                                .userInfo(UserInfo.builder().nickName("用户2").build()).build()
                )
        );

        var actionList = commentActionService.pullActions(user,Page.of(1,6));

        assertEquals(6,actionList.size());

        for(var i : actionList){
            assertEquals(user,i.getUser());
        }

        assertEquals("评论了\"内容1标题\"",actionList.get(0).getSummary().getTitle());
        assertEquals("评论了\"章节1\"",actionList.get(2).getSummary().getTitle());
        assertEquals("评论了\"用户1的动态\"",actionList.get(4).getSummary().getTitle());

    }
}