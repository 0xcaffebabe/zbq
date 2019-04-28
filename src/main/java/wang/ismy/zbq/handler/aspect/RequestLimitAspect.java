package wang.ismy.zbq.handler.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.zbq.annotations.Limit;
import wang.ismy.zbq.annotations.Permission;
import wang.ismy.zbq.model.dto.RequestLimitDTO;
import wang.ismy.zbq.resources.R;
import wang.ismy.zbq.util.ErrorUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author my
 */
@Aspect
@Component
@Order(1)
public class RequestLimitAspect {

    public static final String REQUEST_LIMIT = "requestLimit";
    public static final int MINTUE = 60000;

    @Pointcut("@annotation(wang.ismy.zbq.annotations.Limit)")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        var session = getCurrentUserSession();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        var a = method.getAnnotation(Limit.class);

        if (session == null) {
            ErrorUtils.error(R.UNKNOWN_ERROR);
        }

        if (session.getAttribute(REQUEST_LIMIT) == null) {
            Map<String, RequestLimitDTO> map = new HashMap<>();
            session.setAttribute(REQUEST_LIMIT, map);
        }
        Map<String, RequestLimitDTO> map = (Map<String, RequestLimitDTO>) session.getAttribute(REQUEST_LIMIT);

        String methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        if (map.get(methodName) == null) {
            RequestLimitDTO dto = new RequestLimitDTO();
            dto.setLastRequestTime(System.currentTimeMillis());
            dto.setRequestCount(0);
            map.put(methodName, dto);
        } else {
            RequestLimitDTO dto = map.get(methodName);
            // 如果当前请求距离上一次请求时间间隔大于60s，则清空计数器
            if (System.currentTimeMillis()-dto.getLastRequestTime() >= MINTUE) {
                dto.setRequestCount(0);
            }

            dto.increaseCount();
            // 如果当前请求计数器的大于设定的阈值，则拒绝此次请求
            if (dto.getRequestCount() > a.maxRequestPerMinute()) {
                ErrorUtils.error(R.REQUEST_FREQUENTLY);
            }

            dto.setLastRequestTime(System.currentTimeMillis());


        }


    }

    private HttpSession getCurrentUserSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }
}
