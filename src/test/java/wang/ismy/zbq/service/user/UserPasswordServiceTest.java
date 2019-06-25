package wang.ismy.zbq.service.user;

import lombok.Setter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.dto.user.PasswordResetDTO;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.service.CacheService;
import wang.ismy.zbq.service.system.EmailService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("用户密码服务测试")
class UserPasswordServiceTest {

    @Mock
    UserService userService;

    @Mock
    UserAccountService userAccountService;

    @Mock
    EmailService emailService;

    @Mock
    CacheService cacheService;

    @InjectMocks
    UserPasswordService service;

    /**
     * @see UserPasswordService#generateRandomCode()
     */
    @Test
    public void 测试生成随机验证码() {
        var s = service.generateRandomCode();

        assertEquals(6, s.length());

        System.out.println("生成的验证码:" + s);
    }

    /**
     * @see UserPasswordService#generateCode(String)
     */
    @Test
    public void 测试生成验证码发送邮箱() {
        when(userAccountService.selectUser(eq(UserAccountEnum.EMAIL), eq("abc@q.com"))).thenReturn(
                User.convert(1)
        );

        service.generateCode("abc@q.com");
        verify(cacheService).put(eq("resetPasswordabc@q.com"),argThat(s->((String)s).length() == 6),eq(1800L));
        verify(emailService).sendtTextMail(eq("abc@q.com"),eq("【转笔圈】你正在重置密码"),anyString());
    }

    /**
     * @see UserPasswordService#resetPassword(PasswordResetDTO)
     */
    @Test
    public void 测试重置密码() {
        PasswordResetDTO dto = new PasswordResetDTO();
        dto.setEmail("715711877@qq.com");
        dto.setCode("VB6554");
        dto.setPassword("123");

        when(cacheService.get("resetPassword"+dto.getEmail())).thenReturn(dto.getCode());

        when(userAccountService.selectUser(eq(UserAccountEnum.EMAIL),eq(dto.getEmail()))).thenReturn(User.convert(1));

        when(userService.update(argThat(u->
                u.getUserId().equals(1)
                && u.getPassword().equals(dto.getPassword())
                ))).thenReturn(1);

        service.resetPassword(dto);

        verify(cacheService).delete("resetPassword"+dto.getEmail());
    }
}