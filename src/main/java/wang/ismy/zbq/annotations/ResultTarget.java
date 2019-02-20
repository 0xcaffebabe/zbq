package wang.ismy.zbq.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
* 被该注解修饰的方法都会被AOP拦截修改，方法返回值都会被修改成Result容器类型
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResultTarget {
}
