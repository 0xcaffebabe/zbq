package wang.ismy.zbq.handler;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Aspect
@Component
public class ErrorPageAspect {

    @Pointcut("@annotation(wang.ismy.zbq.annotations.ErrorPage)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint){
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("error");
            modelAndView.addObject("error",throwable.getMessage());
            return modelAndView;
        }

    }
}
