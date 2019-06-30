package wang.ismy.zbq.service.friend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wang.ismy.zbq.dao.friend.FriendMapper;
import wang.ismy.zbq.model.entity.friend.Friend;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("好友服务测试")
class FriendServiceTest {


    @Mock FriendMapper mapper;

    @Mock UserService userService;

    @Mock FriendAddService friendAddService;

    @Mock ExecuteService executeService;

    @InjectMocks
    FriendService friendService;

    @Test
    public void 测试获取朋友信息() {

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(mapper.selectFriendBy2User(eq(1),eq(2))).thenReturn(new Friend());

        when(userService.selectByPrimaryKey(eq(2))).thenReturn(
                User.builder().userId(1)
                .userInfo(UserInfo.builder().nickName("用户2").build()).build()
        );

        var vo = friendService.getFriendInfo(2);

        assertEquals("用户2",vo.getNickName());
    }

    @Test
    public void 测试删除朋友关系() {
        when(mapper.selectFriendBy2User(eq(1),eq(2))).thenReturn(new Friend());

        when(mapper.deleteBy2User(eq(1),eq(2))).thenReturn(2);

        friendService.releaseFriend(1,2);
    }
}