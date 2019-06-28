package wang.ismy.zbq.dao.friend;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.Page;
import wang.ismy.zbq.model.entity.friend.Friend;

import java.util.List;

public interface FriendMapper {

    /**
     * 根据用户ID分页地查询他的朋友列表
     *
     * @param userId 用户ID
     * @param page   分页组件
     * @return 朋友列表
     */
    List<Friend> selectFriendByUserIdPaging(@Param("userId") Integer userId, @Param("page") Page page);

    List<Friend> selectFriendByUserIdAndNickname(@Param("userId") Integer userId, @Param("nickName") String nickName);

    List<Friend> selectRecommendFriendByUserId(@Param("userId") Integer userId);

    Friend selectFriendBy2User(@Param("user1") int user1, @Param("user2") int user);

    int insert(@Param("user1") Integer user1, @Param("user2") Integer user2);

    int countByUserId(Integer userId);

    List<Integer> selectFriendListByUserId(Integer userId);

    /**
     * 查询出好友数量前2的用户
     */
    List<Integer> selectTop2ByUserIdOrderByFriendCount(Integer userId);
}
