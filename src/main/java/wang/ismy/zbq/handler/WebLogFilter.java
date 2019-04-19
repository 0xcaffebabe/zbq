package wang.ismy.zbq.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.ismy.zbq.entity.WebLog;
import wang.ismy.zbq.service.system.LogService;
import wang.ismy.zbq.service.user.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author my
 */
@WebFilter
@Component
public class WebLogFilter implements Filter {

    public static final String X_REAL_IP = "X-Real-Ip";
    public static final String USER_AGENT = "User-Agent";
    @Autowired
    private LogService logService;

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI()+(request.getQueryString()==null?"":"?"+request.getQueryString());
        String ip = request.getRemoteAddr();
        if (request.getHeader(X_REAL_IP) != null){
            ip=request.getHeader(X_REAL_IP);
        }
        String ua = request.getHeader(USER_AGENT);
        Integer user = null;
        try {
            user = userService.getCurrentUser().getUserId();
        }catch (Exception e){
            //TODO
        }

        long time = System.currentTimeMillis();
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }catch (Exception e){
            //TODO
        }

        int timeConsuming = (int) (System.currentTimeMillis()-time);

        WebLog webLog = new WebLog();
        webLog.setUrl(url);
        webLog.setUa(ua);
        webLog.setTimeConsuming(timeConsuming);
        webLog.setCreateTime(LocalDateTime.now());
        webLog.setIp(ip);
        webLog.setUser(user);
        logService.pushWebLog(webLog);
    }

    @Override
    public void destroy() {

    }
}
