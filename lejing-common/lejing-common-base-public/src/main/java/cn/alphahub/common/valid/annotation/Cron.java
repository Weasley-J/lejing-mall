package cn.alphahub.common.valid.annotation;


import cn.alphahub.common.valid.validator.CronExpressionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Cron expression validation for java annotation
 * <p>用于支持Spring MVC校验Cron表达式的注解
 * <p>
 * <b>被校验的元素为<span style="color :red">{@code String}</span>类型<br/></b>
 * <span style="color :red">注解校验示例：</span>
 * <pre>
 *  // Job任务 - 数据对象
 *  &#64;Data
 *  &#64;Accessors(chain = true)
 * public static class JobDomain implements Serializable {
 *     private static final long serialVersionUID = 1L;
 *
 *     &#64;NotNull(message = "任务名称不能为空", groups = {InsertGroup.class, EditGroup.class, QueryGroup.class})
 *     private String jobName;
 *
 *     private String jobGroup;
 *
 *     &#64;NotBlank(message = "任务执行类的全限定类名不能为空", groups = {InsertGroup.class, EditGroup.class})
 *     private String jobClass;
 *
 *     &#64;NotBlank(message = "cron执行表达式不能为空", groups = {InsertGroup.class, EditGroup.class})
 *     &#64;Cron(message = "cron表达式不合法", groups = {InsertGroup.class, EditGroup.class})
 *     private String cronExpression;
 * }
 * </pre>
 * Spring MVC会在参数绑定之前校验被{@code @Cron}注解的元素，如果前端提交的cron表达式不合法，MVC会抛异常（参数校验失败）；
 * 我们可以在{@code @RestControllerAdvice}标注的全局异常处理中给到前端正确的提示
 * </p>
 *
 * @author liuwenjing
 * @version 1.0.1
 * @date 2021年8月4日21:26:37
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {CronExpressionValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface Cron {

    /**
     * @return 提示信息，校验错误后给出的提示信息
     */
    String message() default "your cron expression is an invalid expression. please check the element you annotated.";

    /**
     * 所属校验分组
     */
    Class<?>[] groups() default {};

    /**
     * 所属payload数组
     */
    Class<? extends Payload>[] payload() default {};
}
