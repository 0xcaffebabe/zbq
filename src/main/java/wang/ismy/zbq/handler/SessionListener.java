package wang.ismy.zbq.handler;

import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * @author my
 */
@WebListener
@Component
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    private final List<HttpSession>  onlineSession = new LinkedList<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {

        synchronized (onlineSession){
            onlineSession.add(se.getSession());
        }


    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        synchronized (onlineSession){
            onlineSession.remove(se.getSession());
        }

    }

    public int countOnLine(){
        return onlineSession.size();
    }

    public HttpSession getById(String sessionId){
        sessionId = sessionId.trim();
        for (var i : onlineSession){
            if (i.getId().equals(sessionId)){
                return i;
            }
        }
        return null;
    }
}
