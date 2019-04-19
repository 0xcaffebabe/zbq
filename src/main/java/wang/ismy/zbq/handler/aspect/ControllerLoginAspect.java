package wang.ismy.zbq.handler.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import wang.ismy.zbq.service.user.UserService;

@Aspect
@Component
public class ControllerLoginAspect {


    @Autowired
    private UserService userService;

    @Pointcut("@annotation(wang.ismy.zbq.annotations.MustLogin)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!userService.hasLogin()){
            if (joinPoint.getTarget().getClass().getAnnotation(Controller.class) != null){
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("error");
                modelAndView.addObject("error","错误，未登录");
                return modelAndView;
            }else{
                throw new RuntimeException("未登录");
            }
        }

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
               throw throwable;
        }


    }
}
