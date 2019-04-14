package wang.ismy.zbq.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* 被该注解修饰的方法都会被切面拦截进行请求次数限制
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Limit {
    int maxRequestPerMinute();
}
