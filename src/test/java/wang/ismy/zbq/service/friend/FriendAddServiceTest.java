package wang.ismy.zbq.service.friend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.friend.FriendAddMapper;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.TemplateEngineService;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.system.InformService;
import wang.ismy.zbq.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("好友添加服务测试")
class FriendAddServiceTest {

    @Mock
    FriendAddMapper friendAddMapper;

    @Mock
    UserService userService;

    @Mock
    FriendService friendService;

    @Mock
    MessageService messageService;

    @Mock
    InformService informService;

    @Mock
    ExecuteService executeService;

    @Mock
    EmailService emailService;

    @Mock
    TemplateEngineService templateEngineService;

    @InjectMocks
    FriendAddService service;


    @Test
    public void 测试朋友添加时发送邮件给接收方() throws Throwable {
        var fromUser = User.builder().userInfo(UserInfo.builder().nickName("用户").build()).build();
        var msg = "验证消息";

        when(userService.selectByPrimaryKey(2)).thenReturn(User.convert(2));
        when(templateEngineService.parseModel(eq("email/friendInform.html"), argThat(map ->
                map.get("user").equals("用户")
                        && map.get("content").equals("验证消息")
        ))).thenReturn("测试模板");

        service.friendInform(2, fromUser, msg);

        verify(emailService).sendHtmlMail(eq(2),eq("【转笔圈】有人申请添加你为笔友"),eq("测试模板"));
    }

}