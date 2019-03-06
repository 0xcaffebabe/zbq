package wang.ismy.zbq.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/*
* 被该注解修饰的方法都会被切面所拦截
* 一旦发生异常，则会返回一个错误页面
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ErrorPage {
}
