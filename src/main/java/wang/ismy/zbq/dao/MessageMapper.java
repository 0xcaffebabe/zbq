package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.message.MessageListDTO;
import wang.ismy.zbq.model.dto.message.UnreadMessageDTO;
import wang.ismy.zbq.model.entity.Message;

import java.util.List;

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

    List<MessageListDTO> selectMessageListByUserId(@Param("userId") Integer userId);

    int updateHasRead(@Param("userId") int userId, @Param("friendId") int friendId);
}
