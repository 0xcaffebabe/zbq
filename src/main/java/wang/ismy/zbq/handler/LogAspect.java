package wang.ismy.zbq.handler;

import com.google.gson.Gson;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * wang.ismy.zbq.service.*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.error("类:"+joinPoint.getSignature().getDeclaringTypeName());
        logger.error("方法:"+joinPoint.getSignature().getName());
        logger.error("参数:"+new Gson().toJson(joinPoint.getArgs()));
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("异常:"+throwable.getMessage());
            throw throwable;
        }
    }
}
