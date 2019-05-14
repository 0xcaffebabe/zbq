package wang.ismy.zbq.model.vo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.UserIdGetter;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserVOTest {


    @Test
    public void a(){

        UserVO vo1 = new UserVO();
        vo1.setUserId(1);

        UserVO vo2 = new UserVO();
        vo2.setUserId(2);

        var list = UserIdGetter.getUserIdList(List.of(vo1,vo2));

        assertEquals(2,list.size());

        assertEquals(java.util.Optional.of(1).get(),list.get(0));

    }
}