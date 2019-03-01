package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.Friend;
import wang.ismy.zbq.entity.FriendAdd;

import java.util.List;

public interface FriendAddMapper {

    int insertNew(FriendAdd friendAdd);

    FriendAdd selectFriendAddByFromUserAndToUser(@Param("from") Integer from,@Param("to") Integer to);

    List<FriendAdd> selectFriendAddListByToUserId(Integer userId);

}
