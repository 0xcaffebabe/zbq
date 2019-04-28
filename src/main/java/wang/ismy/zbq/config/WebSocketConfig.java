package wang.ismy.zbq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import wang.ismy.zbq.handler.SessionListener;
import wang.ismy.zbq.service.BroadcastService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author my
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new MyWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketHandler(), "/socket");
    }
}

@Slf4j
class MyWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private SessionListener sessionListener;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        broadcastService.heartBeat(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {


        log.info("建立{}", session);


        String jSessionId = "";

        for (var i : session.getHandshakeHeaders().get("Cookie")) {

            log.info(i);

            String[] arr = i.split(";");
            if (arr.length > 1){
                for (var j : arr){
                    if (j.contains("sessionid_front")) {
                        jSessionId = j.replaceAll("sessionid_front=", "");
                    }
                }

            }else{
                if (i.contains("sessionid_front")) {
                    jSessionId = i.replaceAll("sessionid_front=", "");
                }
            }

        }

        log.info("jsessionId:{}",jSessionId);


        HttpSession httpSession = sessionListener.getById(jSessionId);

        if (httpSession == null) {
            session.close();
        }
        broadcastService.addClient(session, httpSession);


    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("关闭{}", session);
        broadcastService.removeClient(session);


    }


}
