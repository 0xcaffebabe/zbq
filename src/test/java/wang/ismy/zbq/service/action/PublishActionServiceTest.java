package wang.ismy.zbq.service.action;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.action.Action;
import wang.ismy.zbq.service.user.UserService;

import java.util.Comparator;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PublishActionServiceTest {

    @Autowired
    PublishActionService publishActionService;

    @Autowired
    UserService userService;
    @Test
    public void testSelect(){
        var list = publishActionService.pullActions(userService.selectByPrimaryKey(2),Page.of(1,10));

        list.sort(Comparator.comparing(Action::getCreateTime));

        for (Action action : list) {

            System.out.println(action);
        }
    }
}