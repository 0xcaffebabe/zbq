package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.entity.Like;
import wang.ismy.zbq.enums.LikeTypeEnum;
import wang.ismy.zbq.service.UserService;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeMapperTest {
    @Autowired
    LikeMapper likeMapper;

    @Autowired
    UserService userService;

    @Test
    public void testInsert(){
        Like like = new Like();
        like.setLikeType(0);
        like.setContentId(2);
        like.setLikeUser(userService.selectByPrimaryKey(1));
        assertEquals(1,likeMapper.insertNew(like));
    }


    @Test
    public void testSelect(){
        var list = likeMapper.selectLikeListByLikeTypeAndContentId(0,1);
        assertEquals(1,list.size());
    }


    @Test
    public void testSelectBatch(){
        var list = likeMapper.selectLikeListByLikeTypeAndContentIdBatch(0, List.of(1,2));
        assertEquals(2,list.size());
    }


    @Test
    public void testSelectOne(){
        Like like = likeMapper.selectLikeByLikeTypeAndContentIdAndUserId(0,1,1);
        assertNotNull(like);
    }


    @Test
    public void testCountLike(){
        var list = likeMapper.countLikeByLikeTypeAndContentIdBatch(1,List.of(14,13));
        System.out.println(list);
    }
}