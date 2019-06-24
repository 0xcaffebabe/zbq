package wang.ismy.zbq.service.user;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.user.UserSettingMapper;
import wang.ismy.zbq.model.dto.UserSettingObject;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserSetting;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("用户设置服务测试")
class UserSettingServiceTest {

    @Mock
    UserSettingMapper mapper;

    @Mock
    UserService userService;

    @InjectMocks
    UserSettingService service;


    @Test
    public void 测试获取用户已有设置() {

        when(userService.selectByPrimaryKey(eq(1))).thenReturn(User.convert(1));

        when(mapper.select(eq(1))).thenReturn(UserSetting.builder().user(User.convert(1))
                .content(new Gson().toJson(UserSettingObject.empty()))
                .build());

        UserSetting setting = service.select(1);

        assertEquals(1, (int) setting.getUser().getUserId());
        assertEquals(new Gson().toJson(UserSettingObject.empty()), setting.getContent());
    }

    @Test
    public void 测试获取用户未有设置() {

        when(userService.selectByPrimaryKey(eq(1))).thenReturn(User.convert(1));
        when(mapper.insertNew(argThat(s ->
                s.getContent().equals(new Gson().toJson(UserSettingObject.empty()))
                        && s.getUser().getUserId().equals(1)
        ))).thenReturn(1);

        UserSetting setting = service.select(1);

        assertNull(setting);
    }

    @Test
    public void 测试更新用户设置() {

        UserSettingObject obj = new UserSettingObject();
        obj.setEmailInform(false);
        obj.setAnonymous(false);

        when(userService.selectByPrimaryKey(eq(1))).thenReturn(User.convert(1));

        when(mapper.select(eq(1))).thenReturn(
                new UserSetting()
        );

        when(mapper.update(argThat(s->
                s.getContent().equals(new Gson().toJson(obj))
                ))).thenReturn(1);

        service.update(obj,1);
    }

}