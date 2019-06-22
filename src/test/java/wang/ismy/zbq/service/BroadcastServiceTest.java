package wang.ismy.zbq.service;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import wang.ismy.zbq.dao.BroadcastMapper;
import wang.ismy.zbq.model.dto.BroadcastDTO;
import wang.ismy.zbq.model.entity.Broadcast;
import wang.ismy.zbq.model.entity.user.User;
import wang.ismy.zbq.model.entity.user.UserInfo;
import wang.ismy.zbq.service.system.ExecuteService;
import wang.ismy.zbq.service.user.UserService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("广播服务测试")
class BroadcastServiceTest {

    @Mock BroadcastMapper broadcastMapper;

    @Mock UserService userService;

    @Mock ExecuteService executeService;

    @InjectMocks
    BroadcastService broadcastService;

    /**
     * @see BroadcastService#currentUserBroadcast(BroadcastDTO)
     */
    @Test
    public void 测试当前用户发送广播() {
        BroadcastDTO dto = new BroadcastDTO();
        dto.setContent("测试内容");
        dto.setAnonymous(false);

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(broadcastMapper.insertNew(argThat(b->
                b.getContent().equals("测试内容")
                && b.getAnonymous().equals(false)
                && b.getUser().getUserId().equals(1)
                ))).thenReturn(1);
        broadcastService.currentUserBroadcast(dto);
    }

    /**
     * @see BroadcastService#selectNewest10()
     */
    @Test
    public void 获取最新10条广播消息() {
        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        when(broadcastMapper.selectNewest10()).thenReturn(
                List.of(
                        Broadcast.builder().content("内容1").user(User.convert(1)).build(),
                        Broadcast.builder().content("内容2").user(User.convert(2)).build(),
                        Broadcast.builder().content("内容3").user(User.convert(3)).build()
                )
        );

        when(userService.selectByUserIdBatch(argThat(l->l.containsAll(List.of(1,2,3))))).thenReturn(
            List.of(
                    User.builder().userId(1).userInfo(UserInfo.builder().nickName("用户1").build()).build(),
                    User.builder().userId(2).userInfo(UserInfo.builder().nickName("用户2").build()).build(),
                    User.builder().userId(3).userInfo(UserInfo.builder().nickName("用户3").build()).build()
            )
        );

        var brocastVO = broadcastService.selectNewest10();

        assertEquals(3,brocastVO.size());
        assertEquals("用户1",brocastVO.get(0).getUser().getNickName());
        assertEquals("内容1",brocastVO.get(0).getContent());
        assertEquals(true,brocastVO.get(0).getSelf());

    }

    /**
     * @see BroadcastService#addClient(WebSocketSession, HttpSession)
     */
    @Test
    public void 测试添加客户端() {

        broadcastService.addClient(mock(WebSocketSession.class),mock(HttpSession.class));

        verify(executeService).submit(any(Runnable.class));
    }

    /**
     * @see BroadcastService#removeClient(WebSocketSession)
     */
    @Test
    public void 测试移除客户端() {

        broadcastService.removeClient(mock(WebSocketSession.class));

        verify(executeService).submit(any(Runnable.class));
    }

    /**
     * @see BroadcastService#broadcast(Broadcast)
     */
    @Test
    public void 测试广播消息() throws IOException {
        List<WebSocketSession> webSocketSessions = List.of(
                mock(WebSocketSession.class),mock(WebSocketSession.class),mock(WebSocketSession.class)
        );

        for(var i : webSocketSessions){
            broadcastService.addClient(i,mock(HttpSession.class));
        }

        Broadcast broadcast = new Broadcast();
        broadcast.setContent("测试内容");
        broadcast.setUser(User.convert(1));

        when(userService.getCurrentUser()).thenReturn(User.convert(1));

        String json = new Gson().toJson(broadcast);

        broadcastService.broadcast(broadcast);

        verify(executeService,times(6)).submit(any(Runnable.class));

    }

    /**
     * @see BroadcastService#sendMsg(TextMessage, WebSocketSession)
     */
    @Test
    public void 测试发送消息() throws Throwable{

        var msg = new TextMessage("payload");
        var session = mock(WebSocketSession.class);

        var method = broadcastService.getClass().getDeclaredMethod("sendMsg", TextMessage.class,WebSocketSession.class);
        method.setAccessible(true);
        method.invoke(broadcastService,msg,session);

        verify(session).sendMessage(argThat(e->
                e.getPayload().equals("payload")
                ));

    }
}