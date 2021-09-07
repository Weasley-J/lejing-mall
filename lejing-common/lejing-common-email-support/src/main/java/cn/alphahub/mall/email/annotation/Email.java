package cn.alphahub.mall.email.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * 提供不同邮件模板发送邮件的注解
 *
 * @author lwj
 * @version 1.0.0
 * @apiNote 基于此注解解析不同的邮件模板, 使用注解@Email指定以哪个模板发送邮件
 */
@Documented
@Target({TYPE, TYPE_USE, TYPE_PARAMETER, METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    /**
     * 默认模板名称
     */
    String DEFAULT_TEMPLATE = "DEFAULT";

    /**
     * 邮件模板名称，默认：DEFAULT
     *
     * @return 邮件模板名称
     */
    String name() default DEFAULT_TEMPLATE;
}
