package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.entity.FriendAdd;

public interface FriendAddMapper {

    int insertNew(FriendAdd friendAdd);

    FriendAdd selectFriendAddByFromUserAndToUser(@Param("from") Integer from,@Param("to") Integer to);
}
