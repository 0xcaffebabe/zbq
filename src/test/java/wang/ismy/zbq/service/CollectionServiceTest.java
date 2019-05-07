package wang.ismy.zbq.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.enums.CollectionTypeEnum;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionServiceTest {

    @Autowired
    CollectionService collectionService;


    @Test
    public void testCollectionCount(){

        var map = collectionService.selectCollectionCountBatchByType(CollectionTypeEnum.CONTENT, List.of(1,2,3),2);

        assertEquals(3,map.values().size());

        assertEquals(2L, java.util.Optional.ofNullable(map.get(3).getCollectionCount()));
    }
}