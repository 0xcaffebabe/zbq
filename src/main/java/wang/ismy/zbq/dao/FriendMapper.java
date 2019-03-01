package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.Friend;

import java.util.List;

public interface FriendMapper {

    List<Friend> selectFriendByUserId(Integer userId);

    List<Friend> selectFriendByUserIdAndNickname(@Param("userId") Integer userId,@Param("nickName") String nickName);

    List<Friend> selectRecommendFriendByUserId(@Param("userId") Integer userId);


}
