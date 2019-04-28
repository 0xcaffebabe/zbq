package wang.ismy.zbq.dao.friend;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.entity.friend.FriendAdd;

import java.util.List;

public interface FriendAddMapper {

    int insertNew(FriendAdd friendAdd);

    FriendAdd selectFriendAddByFromUserAndToUser(@Param("from") Integer from,@Param("to") Integer to);

    List<FriendAdd> selectFriendAddListByToUserId(Integer userId);

    FriendAdd selectFriendAddByPrimaryKey(Integer friendAddId);

    int updateVisible(Integer friendAddId);
}
