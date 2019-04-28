package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.model.dto.message.MessageListDTO;
import wang.ismy.zbq.model.dto.message.UnreadMessageDTO;
import wang.ismy.zbq.model.entity.Message;

import java.util.List;

public interface MessageMapper {

    List<Message> selectMessageListBy2User(@Param("user1") Integer user1,@Param("user2") Integer user2);

    int insert(Message message);

    List<UnreadMessageDTO> selectUnreadMessageByUserId(@Param("userId") Integer userId);

    List<MessageListDTO> selectMessageListByUserId(@Param("userId") Integer userId);

    int updateHasRead(@Param("userId") int userId,@Param("friendId") int friendId);
}
