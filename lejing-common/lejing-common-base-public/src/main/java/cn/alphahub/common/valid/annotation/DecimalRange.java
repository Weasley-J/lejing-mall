package cn.alphahub.common.valid.annotation;


import cn.alphahub.common.valid.validator.BigDecimalRangeConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 带注释的元素必须在适当的范围内，适用于高精度数值类型{@code BigDecimal}
 * <p>
 * 支持的类型是:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 * </ul>
 *  注意：最小值与最大值的入参以{@code String}类型传入
 * <p>
 * <b>用法示例：</b>
 * <pre>
 *
 * <code>@Data</code>
 * public class VirtualMoneyRequest implements Serializable {
 *     private static final long serialVersionUID = 1L;
 *
 *     //大会员id
 *     private String outMemberId;
 *
 *     //当前付款金额（最低为一分钱）
 *     <code>@NotNull(message = "当前订单的付款金额不能为空")</code>
 *     <code>@DecimalRange(min = "0.00", max = "999999.00", message = "订单的付款金额必须在区间[0,999999]内</code>
 *     private BigDecimal paymentAmount;
 * }
 * </pre>
 *
 * @author liuwenjing
 * @date 2021年4月21日13:52:18
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {BigDecimalRangeConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface DecimalRange {

    /**
     * 提示信息,不设置，默认返回classpath下面的默认提示
     *
     * @return 提示信息
     */
    String message() default "必须提交指定区间内的值: min <= input <= max";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 默认最小值: 0
     *
     * @return 元素的值必须大于或等于
     */
    String min() default "0";

    /**
     * 默认最大值{@code Long.MAX_VALUE}
     *
     * @return 元素的值必须小于或等于
     */
    String max() default "" + Long.MAX_VALUE;
}
