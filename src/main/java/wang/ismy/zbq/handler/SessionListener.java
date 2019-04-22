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

    private List<HttpSession> onlineSession = new LinkedList<>();

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        onlineSession.add(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        onlineSession.remove(se.getSession());
    }

    public int countOnLine(){
        return onlineSession.size();
    }
}
