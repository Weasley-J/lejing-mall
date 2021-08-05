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
 * 用于验证字符串数组中的值，用于支持Spring MVC校验枚举值的注解，
 * 避免代码中写大量的{@code if}判断，保持了代码的简洁和完整性
 * <p>
 * <b>被校验的元素须为<span style="color :red">{@code String}</span>类型<br/></b>
 * <span style="color :red">注解校验示例：</span>
 * <pre>
 *  // 虚拟币 - 数据对象
 *  &#64;Data
 *  &#64;Accessors(chain = true)
 * public class VirtualCoin implements Serializable {
 *     private static final long serialVersionUID = 1L;
 *
 *     &#64;NotNull(message = "会员id不能为空")
 *     private Long memberId;
 *
 *    &#64;DecimalRange(min = "-12000", max = "11000", message = "虚拟币金额必须在区间[-12000,11000]内")
 *     private BigDecimal virtualValue;
 *
 *     &#64;IncludeValue(value = {"INCOME", "EXPENDITURE"}, message = "收支类型只能提交{INCOME,EXPENDITURE}内的字典值")
 *     private String incomeExpenditureType;
 *
 *     &#64;ListValue(value = {-1, 0, 1}, message = "虚拟币状态只能提交{-1, 0, 1}内的值")
 *     private Integer status;
 * }
 * </pre>
 * 上面列子中，由于我们的Java Bean对象加上了{@code @IncludeValue}的注解, 因此，
 * 前端提交的表单时，{@code incomeExpenditureType}字段只能提交{@code value}内的可选值{"INCOME", "EXPENDITURE"}内的任意一个；
 * Spring MVC会在参数绑定之前校验被{@code @IncludeValue}注解校验的元素，如果前端提交的字符串不是我们限定范围内的值，MVC会抛异常（参数校验失败）；
 * 我们可以在{@code @RestControllerAdvice}标注的全局异常处理中给到前端提示，避免代码中写大量的{@code if}判断，保持了代码的简洁
 * </p>
 *
 * @author liuwenjing
 * @version 1.0.1
 * @date 2021年8月4日21:26:37
 */
@Documented
@Retention(RUNTIME)
@Constraint(validatedBy = {StringArrayConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
public @interface IncludeValue {

    /**
     * @return 提示信息，校验错误后给出的提示信息
     */
    String message() default "必须提交指定范围中的字符串";

    /**
     * @return 默认元素字符数组
     */
    String[] value() default {};

    /**
     * 所属校验分组
     */
    Class<?>[] groups() default {};

    /**
     * 所属payload数组
     */
    Class<? extends Payload>[] payload() default {};
}
