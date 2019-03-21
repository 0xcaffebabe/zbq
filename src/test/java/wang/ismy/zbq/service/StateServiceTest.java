package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.dto.StateCommentDTO;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StateServiceTest {

    @Autowired
    StateService stateService;

    @Autowired
    UserService userService;

    @Test
    public void testSelect(){
        long time = System.currentTimeMillis();
        userService.setTestUser(userService.selectByPrimaryKey(1));
        var list = stateService.selectCurrentUserStatePaging(Page.builder().pageNumber(1).length(5).build());

        System.out.println("time:"+(System.currentTimeMillis()-time));
    }


    @Test
    public void testCreateComment(){

        userService.setTestUser(userService.selectByPrimaryKey(1));
        StateCommentDTO  dto = new StateCommentDTO();
        dto.setToUser(1);
        dto.setContent("我@我自己");
        dto.setStateId(10);

        assertEquals(1,stateService.createCurrentUserStateComment(dto));

    }


    @Test
    public void testDelete(){

        userService.setTestUser(userService.selectByPrimaryKey(1));

        stateService.deleteCurrentUserStateById(17);
    }
}