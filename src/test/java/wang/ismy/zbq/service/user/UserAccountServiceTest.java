package wang.ismy.zbq.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.user.UserAccountMapper;
import wang.ismy.zbq.enums.UserAccountEnum;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserAccount;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.system.EmailService;
import wang.ismy.zbq.service.system.ExecuteService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("用户账户服务测试")
class UserAccountServiceTest {

    @Mock UserAccountMapper accountMapper;

    @Mock UserService userService;

    @Mock EmailService emailService;

    @Mock ExecuteService executeService;

    @InjectMocks
    UserAccountService service;

    /**
     * @see UserAccountService#selectAccountName(UserAccountEnum, Integer)
     */
    @Test
    public void 测试获取账户名() {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccountName("账户名");
        when(accountMapper.selectByAccountTypeAndUserId(eq(UserAccountEnum.EMAIL.getCode()),eq(1)))
                .thenReturn(
                        userAccount
                );


        assertEquals("账户名",service.selectAccountName(UserAccountEnum.EMAIL,1));
    }

    /**
     * @see UserAccountService#currentUserBindEmail(String)
     */
    @Test
    public void 测试当前用户绑定邮箱() {
        when(userService.getCurrentUser()).thenReturn(User.builder().userId(1).username("user1").build());

        service.currentUserBindEmail("715711877@qq.com");
        verify(executeService).submit(any(Runnable.class));
    }


}