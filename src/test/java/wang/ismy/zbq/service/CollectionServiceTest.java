package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.enums.CollectionTypeEnum;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.service.user.UserService;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionServiceTest {

    @Autowired
    CollectionService collectionService;

    @Autowired
    UserService userService;

    @Test
    public void testCollectionCount(){

        var map = collectionService.selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT, List.of(1,2,3),2);

        assertEquals(3,map.values().size());

        assertEquals(2L, java.util.Optional.ofNullable(map.get(3).getCollectionCount()));
    }


    @Test
    public void selectCollectionList(){

        userService.setCurrentUser(userService.selectByPrimaryKey(2));
        var list = collectionService.selectCurrentUserCollectionList(Page.of(1,10));

        assertEquals(4,list.size());

        assertEquals("【转】Dr.ct改法教学",list.get(0).getSummary());
    }
}