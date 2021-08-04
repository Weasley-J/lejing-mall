package cn.alphahub.common.valid.annotation;

import cn.alphahub.common.valid.validator.StringArrayConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用于验证字符串数组中的值
 * <p>被校验的元素须为<b style="color :red">{@code String}</b>类型</p>
 *
 * @author liuwenjing
 * @date 2021年8月4日21:26:37
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {StringArrayConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface IncludeString {

    /**
     * @return 默认元素字符数组
     */
    String[] values() default {};

    /**
     * @return 提示信息，校验错误后给出的提示信息
     */
    String message() default "必须提交指定范围中的字符串";

    /**
     * 所属校验分组
     */
    Class<?>[] groups() default {};

    /**
     * 所属payload数组
     */
    Class<? extends Payload>[] payload() default {};
}
