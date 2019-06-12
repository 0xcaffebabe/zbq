package wang.ismy.zbq.service.user;

import lombok.Getter;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.user.UserMapper;
import wang.ismy.zbq.handler.SessionListener;
import wang.ismy.zbq.model.dto.user.RegisterDTO;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.LoginACLService;
import wang.ismy.zbq.service.MessageService;
import wang.ismy.zbq.service.PermissionService;
import wang.ismy.zbq.service.SessionService;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.system.ExecuteService;

import javax.inject.Inject;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("用户服务测试")
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserMapper mapper;

    @Mock
    UserInfoService userInfoService;

    @Mock
    PermissionService permissionService;

    @Mock
    LoginACLService loginACLService;

    @Mock
    FriendService friendService;

    @Mock
    MessageService messageService;

    @Mock
    ExecuteService executeService;

    @Mock
    UserLoginLogService userLoginLogService;

    @Mock
    SessionListener sessionListener;

    @Mock
    SessionService sessionService;

    /**
     * @see UserService#createNewUser(RegisterDTO)
     */
    @Test
    public void 测试创建新用户应该成功() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("username");
        dto.setPassword("123");

        when(userInfoService.insertUserInfo(argThat(userInfo -> {

                    userInfo.setUserInfoId(1);
                    return userInfo.getNickName().equals("佚名")
                            && userInfo.getProfile().equals("/img/anonymous.jpg")
                            && userInfo.getBirthday().equals(LocalDate.now())
                            && userInfo.getPenYear().equals(1)
                            && userInfo.getRegion().equals("中国")
                            && userInfo.getGender().equals(0)
                            && userInfo.getDescription().equals("这个人很懒，没有留下介绍");
                }

        ))).thenReturn(1);

        when(mapper.insert(argThat(user -> {
                    user.setUserId(1);
                    return user.getUsername().equals(dto.getUsername())
                            && user.getPassword().equals(dto.getPassword())
                            && user.getUserInfo().getNickName().equals("佚名")
                            && user.getUserInfo().getProfile().equals("/img/anonymous.jpg")
                            && user.getUserInfo().getBirthday().equals(LocalDate.now())
                            && user.getUserInfo().getPenYear().equals(1)
                            && user.getUserInfo().getRegion().equals("中国")
                            && user.getUserInfo().getGender().equals(0)
                            && user.getUserInfo().getDescription().equals("这个人很懒，没有留下介绍")
                            && user.getUserPermission().getLogin()
                            && !user.getUserPermission().getContentPublish()
                            && !user.getUserPermission().getCoursePublish()
                            && user.getUserInfo().getUserInfoId().equals(1);
                }

        ))).thenReturn(1);
        when(permissionService.insertPermission(argThat(permission -> {
            permission.setUserPermissionId(1);
            return true;
        }))).thenReturn(1);

        when(loginACLService.insertNew(1)).thenReturn(1);

        assertEquals(1, userService.createNewUser(dto));
        verify(friendService).insertNewRelation(eq(0), eq(1));
        verify(friendService).insertNewRelation(eq(1), eq(0));
        verify(messageService).sendMessage(argThat(user -> user.getUserId().equals(0)), argThat(msg ->
                msg.getTo().equals(1) && msg.getContent().equals("欢迎来到转笔圈，有不懂的可以问我")
        ));

        verify(sessionService).putInSession(eq("user"), eq(null));
        verify(executeService).submit(any(Runnable.class));

    }

    /**
     * @see UserService#login(String, String, String)
     */
    @Test
    public void 测试登陆应该成功() {
        when(mapper.selectByUsername(eq("user")))
                .thenReturn(User.builder().userId(1).username("user").password("123").build());
        when(loginACLService.canLogin(eq(1))).thenReturn(true);

        userService.login("user","123","127.0.0.1");
        verify(userLoginLogService).addLog(argThat(log ->
                log.getLoginIp().equals("127.0.0.1")
                        && log.getLoginUser().getUserId().equals(1)
        ));
        verify(sessionService).putInSession(eq("user"), argThat(
                obj ->
                        ((User) obj).getUserId().equals(1)
        ));

    }

    /**
     * @see UserService#getCurrentUser()
     */
    @Nested
    class 获取用户测试集{
        @Test
        public void 测试获取当前用户成功() {
            when(sessionService.getFromSession(eq("user"))).thenReturn(
                    User.convert(1)
            );
            assertEquals(User.convert(1),userService.getCurrentUser());
        }

        @Test
        public void 测试获取当前用户失败() {
            assertNull(userService.getCurrentUser());
        }
    }

    /**
     * @see UserService#refreshCurrentUser()
     */
    @Test
    public void 测试刷新当前用户() {
        when(mapper.selectByUsername(eq("user"))).thenReturn(User.convert(1));
        when(sessionService.getFromSession(eq("user"))).thenReturn(User.builder().userId(1).username("user").build());
        userService.refreshCurrentUser();
        verify(sessionService).putInSession(eq("user"),argThat(user->user instanceof User));
    }

    /**
     * @see UserService#setCurrentUser(User)
     */
    @Test
    public void 测试设置当前用户() {
        var user = User.convert(1);
        userService.setCurrentUser(user);
        verify(sessionService).putInSession(eq("user"),eq(user));
        verify(executeService).submit(any(Runnable.class));
    }

    /**
     * @see UserService#userList2UserVOMap(List)
     */
    @Test
    public void 测试用户列表转用户视图表() {
        var userList = List.of(
                User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build(),
                User.builder().userId(3).userInfo(UserInfo.builder().nickName("用户3").build()).build()
        );

        var map = UserService.userList2UserVOMap(userList);
        assertEquals("用户1",map.get(1).getNickName());
        assertEquals("用户2",map.get(2).getNickName());
    }

}