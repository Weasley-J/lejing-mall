package cn.alphahub.common.valid.custom;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 校验分组-自定义校验注解
 * <b>校验值范围</b>
 *
 * @author liuwenjing
 */
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ListValue {
    /**
     * 提示信息
     *
     * @return 不设置，默认返回classpath下面的默认提示
     */
    String message() default "{cn.alphahub.common.valid.custom.ListValue.message}";

    /**
     * 所属分组
     *
     * @return 分组class数据
     */
    Class<?>[] groups() default {};

    /**
     * payload
     *
     * @return Payload数组
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 默认值列表
     *
     * @return 默认值数组
     */
    int[] values() default {};
}
