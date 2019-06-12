package wang.ismy.zbq.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author my
 */
@Service
public class SessionService {

    public void putInSession(String key, Object value) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute(key, value);
    }

    public Object getFromSession(String key){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession().getAttribute(key);
    }
}
