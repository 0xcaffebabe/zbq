package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.State;
import wang.ismy.zbq.service.UserService;

import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringRunner.class)
@SpringBootTest

public class StateMapperTest {

    @Autowired
    StateMapper stateMapper;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){

        userService.setTestUser(userService.selectByPrimaryKey(1));

        State state = new State();
        state.setContent("VGG便宜出了");
        state.setUser(userService.getCurrentUser());
        var ret = stateMapper.insertNew(state);
        assertEquals(1,ret);
    }


    @Test
    public void testSelect(){
        var list = stateMapper.selectStateByUserIdBatch(List.of(1,2));
        assertEquals(3,list.size());

    }
}