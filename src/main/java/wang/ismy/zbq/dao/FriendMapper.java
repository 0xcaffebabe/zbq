package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.Page;
import wang.ismy.zbq.entity.Friend;

import java.util.List;

public interface FriendMapper {

    List<Friend> selectFriendByUserIdPaging(@Param("userId") Integer userId, @Param("page")Page page);

    List<Friend> selectFriendByUserIdAndNickname(@Param("userId") Integer userId,@Param("nickName") String nickName);

    List<Friend> selectRecommendFriendByUserId(@Param("userId") Integer userId);

    Friend selectFriendBy2User(@Param("user1") int user1,@Param("user2") int user);

    int insert(@Param("user1") Integer user1,@Param("user2") Integer user2);

    int countByUserId(Integer userId);

}
