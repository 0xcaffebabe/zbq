package wang.ismy.zbq.service.system;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.entity.Comment;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.SubscriptionService;
import wang.ismy.zbq.service.TemplateEngineService;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserAccountService;
import wang.ismy.zbq.service.user.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@DisplayName("通知服务测试")
@ExtendWith(MockitoExtension.class)
class InformServiceTest {

    @InjectMocks
    InformService informService;

    @Mock
    MessageService messageService;

    @Mock
    FriendService friendService;

    @Mock
    UserService userService;

    @Mock
    ExecuteService executeService;

    @Mock
    EmailService emailService;

    @Mock
    UserAccountService userAccountService;

    @Mock
    TemplateEngineService templateEngineService;

    @Mock
    SubscriptionService subscriptionService;

    @Test
    public void 测试评论通知发送() throws Throwable {
        User commentUser = User.builder().userId(1).
                userInfo(UserInfo.builder().nickName("用户1").build()).build();
        Integer authorId = 2;
        Comment comment = Comment.builder().
                fromUser(commentUser).content("测试评论").build();
        String type = "类型";
        String content = "主题";

        when(userService.selectByPrimaryKey(eq(authorId))).thenReturn(User.convert(authorId));
        when(templateEngineService.parseStr(eq(TemplateEngineService.COMMENT_TEMPLATE), argThat(map ->
                map.size() == 5
        ))).thenReturn("测试消息");

        when(templateEngineService.parseModel(eq("email/commentInform.html"), argThat(map ->
                map.size() == 5
        ))).thenReturn("测试模板");

        informService.informUserComment(commentUser, authorId, comment, type, content);


        verify(emailService).sendHtmlMail(eq(2), eq("【转笔圈】评论通知"), eq("测试模板"));
        verify(executeService).submit(any(Runnable.class));
    }

    @Test
    public void 测试内容通知发送() throws Throwable {
        Integer authorId = 2;
        String content = "主题";

        when(userService.selectByPrimaryKey(eq(authorId))).thenReturn(User.builder().userInfo(UserInfo.builder().nickName("用户2").build()).build());
        when(templateEngineService.parseStr(eq(TemplateEngineService.CONTENT_TEMPLATE), argThat(map ->
                map.get("user").equals("用户2")
                && map.get("content").equals("主题")
        ))).thenReturn("测试消息");

        when(templateEngineService.parseModel(eq("email/contentInform.html"), argThat(map ->
                map.get("user").equals("用户2")
                        && map.get("content").equals("主题")
        ))).thenReturn("测试模板");

        when(subscriptionService.selectSubscriperAll(eq(2))).thenReturn(List.of(1,3,4,5));

        informService.informUserContent(authorId, content);

        verify(executeService,times(2)).submit(any(Runnable.class));
    }


}