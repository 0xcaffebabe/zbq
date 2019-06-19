package wang.ismy.zbq.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.MessageMapper;
import wang.ismy.zbq.model.dto.message.MessageDTO;
import wang.ismy.zbq.model.dto.message.UnreadMessageDTO;
import wang.ismy.zbq.model.entity.Message;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.friend.FriendService;
import wang.ismy.zbq.service.user.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("消息服务测试")
class MessageServiceTest {

    @Mock
    MessageMapper mapper;

    @Mock
    UserService userService;

    @Mock
    FriendService friendService;

    @InjectMocks
    MessageService messageService;

    /**
     * @see MessageService#selectCurrentUserMessageListByFriendId(int)
     */
    @Test
    public void 测试拉取用户与朋友的消息列表() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));
        when(userService.selectByPrimaryKey(eq(2))).thenReturn(User.convert(2));

        when(friendService.isFriend(eq(1), eq(2))).thenReturn(true);
        when(mapper.selectMessageListBy2User(eq(2), eq(1))).thenReturn(
                List.of(
                        Message.builder().content("消息1")
                                .fromUser(User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build()).build(),
                        Message.builder().content("消息2")
                                .fromUser(User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build()).build(),
                        Message.builder().content("消息3")
                                .fromUser(User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build()).build()
                )
        );
        messageService.selectCurrentUserMessageListByFriendId(2);

        verify(mapper).updateHasRead(eq(1), eq(2));


    }

    /**
     * @see MessageService#sendMessage(User, MessageDTO)
     */
    @Test
    public void 发送消息测试() {
        User currentUser = User.convert(1);
        MessageDTO dto = new MessageDTO();
        dto.setTo(2);
        dto.setContent("消息内容");

        when(friendService.isFriend(eq(2), eq(1)))
                .thenReturn(true);

        when(mapper.insert(argThat(msg ->
                msg.getFromUser().equals(currentUser)
                        && msg.getToUser().getUserId().equals(2)
                        && msg.getContent().equals("消息内容")
        ))).thenReturn(1);

        assertTrue(messageService.sendMessage(currentUser, dto));

    }

    /**
     * @see MessageService#selectCurrentUserUnreadMessageList()
     */
    @Test
    public void 测试拉取用户未读消息列表() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(mapper.selectUnreadMessageByUserId(eq(1))).thenReturn(
                List.of(
                        UnreadMessageDTO.builder().fromUser(1).newestMsg("消息1").build(),
                        UnreadMessageDTO.builder().fromUser(2).newestMsg("消息2").build(),
                        UnreadMessageDTO.builder().fromUser(3).newestMsg("消息3").build()
                )
        );

        when(userService.selectByUserIdBatch(argThat(l->l.containsAll(List.of(1,2,3)))))
                .thenReturn(
                        List.of(
                                User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                                User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build(),
                                User.builder().userId(3).userInfo(UserInfo.builder().nickName("用户3").build()).build()
                        )
                );
        var list = messageService.selectCurrentUserUnreadMessageList();

        assertEquals(3,list.size());
        assertEquals("消息1",list.get(0).getNewestMsg());
        assertEquals("用户1",list.get(0).getFromUserInfo().getNickName());

    }


}