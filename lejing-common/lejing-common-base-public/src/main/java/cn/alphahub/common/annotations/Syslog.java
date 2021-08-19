package cn.alphahub.common.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 * <p>用来记录系统日志的注解</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/19
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Syslog {
    /**
     * 系统日志名称、描述
     *
     * @return 系统日志名称、描述
     */
    String name() default "";
}
