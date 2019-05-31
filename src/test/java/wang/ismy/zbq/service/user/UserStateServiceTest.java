package wang.ismy.zbq.service.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;

import javax.inject.Inject;
import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserStateServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserStateService userStateService;

    @Test
    public void test1() {
        User user = new User();
        user.setUserId(1);
        user.setUserInfo(new UserInfo());
        user.getUserInfo().setNickName("test");
        user.getUserInfo().setProfile("http://baidu.com");

        when(userService.selectByPrimaryKey(1)).thenReturn(user);

        var ret = userStateService.selectByUserId(1);
        assertEquals("test",ret.getNickName());
        verify(userService).selectByPrimaryKey(1);

    }

    @Test
    public void test2() {

    }
}