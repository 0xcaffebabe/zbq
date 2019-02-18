package wang.ismy.zbq.handler;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import wang.ismy.zbq.dto.Result;

@Aspect
@Component
public class ControllerResultAspect {

    @Pointcut("@annotation(wang.ismy.zbq.annotations.ResultTarget)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Exception {
        System.out.println(joinPoint);
        try {
            Result result = new Result();
            result.setMsg("success");
            result.setData(joinPoint.proceed());
            result.setSuccess(true);
            return result;
        } catch (Throwable throwable) {
            Result result = new Result();
            result.setSuccess(false);
            result.setMsg(throwable.getMessage());
            return result;
        }
    }
}
