package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.message.MessageListDTO;
import wang.ismy.zbq.model.dto.message.UnreadMessageDTO;
import wang.ismy.zbq.model.entity.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MessageMapper {

    /**
     * 根据两个用户查询消息列表
     *
     * @param user1 用户ID1
     * @param user2 用户ID2
     * @return 消息列表
     */
    List<Message> selectMessageListBy2User(@Param("user1") Integer user1, @Param("user2") Integer user2);

    int insert(Message message);

    /**
     * 根据用户ID查询未读消息列表
     *
     * @param userId 用户ID
     * @return 未读消息视图列表
     */
    List<UnreadMessageDTO> selectUnreadMessageByUserId(@Param("userId") Integer userId);

    /**
     * 查询出用户最近聊天列表
     *
     * @param userId 用户ID
     * @param list   朋友ID列表
     * @return 消息列表视图对象列表
     */
    List<MessageListDTO> selectRecentMessageList(@Param("userId") Integer userId, @Param("list") List<Integer> list);

    int updateHasRead(@Param("userId") int userId, @Param("friendId") int friendId);

    /**
     * 查询出用户最近聊天的用户，根据时间排序
     *
     * @param user 用户ID
     * @return 哈希表列表
     */
    List<Map<String, Integer>> selectRecentMessageUser(@Param("user") Integer user);
}
