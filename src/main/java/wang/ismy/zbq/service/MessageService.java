package wang.ismy.zbq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.dto.MessageDTO;
import wang.ismy.zbq.entity.Message;
import wang.ismy.zbq.entity.User;
import wang.ismy.zbq.resources.StringResources;
import wang.ismy.zbq.util.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {


    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    public List<MessageDTO> selectCurrentUserMessageListByFriendId(int friendId){
        User user = userService.getCurrentUser();
        if (!friendService.isFriend(user.getUserId(),friendId)){
            ErrorUtils.error(StringResources.NOT_FRIEND);
        }

        var list = messageMapper.selectMessageListBy2User(friendId,user.getUserId());

        List<MessageDTO> ret = new ArrayList<>();
        for (var i : list){
            MessageDTO dto = MessageDTO.builder()
                    .sender(i.getFromUser())
                    .content(i.getContent())
                    .sendTime(i.getCreateTime())
                    .build();
            ret.add(dto);
        }
        return ret;

    }
}
