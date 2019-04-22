package wang.ismy.zbq.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.zbq.dao.friend.FriendMapper;
import wang.ismy.zbq.entity.friend.Friend;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FriendMapperTest {

    @Autowired
    FriendMapper friendMapper;



    @Test
    public void testCount(){
        assertEquals(7,friendMapper.countByUserId(1));
    }


    @Test
    public void testRecommend(){

        List<Friend> friendList = friendMapper.selectRecommendFriendByUserId(1);
        assertEquals("404",friendList.get(0).getFriendUserInfo().getNickName());
    }


    @Test
    public void testSearch(){
        List<Friend> friends = friendMapper.selectFriendByUserIdAndNickname(1,"m");
        assertEquals(1,friends.size());
        assertEquals("my",friends.get(0).getFriendUserInfo().getNickName());
    }


    @Test
    public void testFriendRelation(){
        Friend friend = friendMapper.selectFriendBy2User(1,2);

        assertNotNull(friend);
    }
}