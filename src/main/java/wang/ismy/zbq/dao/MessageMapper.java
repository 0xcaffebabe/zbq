package wang.ismy.zbq.dao;

import org.apache.ibatis.annotations.Param;
import wang.ismy.zbq.dto.UnreadMessageDTO;
import wang.ismy.zbq.entity.Message;

import java.util.List;

public interface MessageMapper {

    List<Message> selectMessageListBy2User(@Param("user1") Integer user1,@Param("user2") Integer user2);

    int insert(Message message);

    List<UnreadMessageDTO> selectUnreadMessageByUserId(@Param("userId") Integer userId);
}
