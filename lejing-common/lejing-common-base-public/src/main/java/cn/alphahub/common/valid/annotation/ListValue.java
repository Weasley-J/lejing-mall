package cn.alphahub.common.valid.annotation;

import cn.alphahub.common.valid.validator.ListValueConstraintValidator;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <b>范围值校验注解<br></b>
 * <b>校验指定范围中的值,用于支持Spring MVC的JSR303入参校验</b>
 * <p>
 * <b>使用示例:</b>
 * <pre>
 * <code>@Data</code>
 * public class VirtualGrantRequest implements Serializable {
 *     private static final long serialVersionUID = 1L;
 *
 *     //虚拟金币核销方式（1 下单发放， 2 下单抵扣，3 退款归还 4 取消订单归还，... ，默认：1 )
 *     <code>@NotNull(message = "核销方式不能为空")</code>
 *     <code>@ListValue(values = {0,1,2,3,4,5,6,7,8}, message = "只能提交指定范围中的值: {0,1,2,3,4,5,6,7,8}")</code>
 *     private Integer creditType;
 * }
 * </pre>
 *
 * @author liuwenjing
 * @date 2021年4月21日10:11:06
 */
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ListValue {

    /**
     * 提示信息，不设置，默认返回classpath下面的默认提示
     */
    String message() default "必须提交指定范围中的值";

    /**
     * 所属Class分组
     */
    Class<?>[] groups() default {};

    /**
     * payload数组
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 默认值列表
     */
    int[] values() default {};
}
