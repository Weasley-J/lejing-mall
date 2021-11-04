package cn.alphahub.common.valid.validator;

import cn.alphahub.common.valid.annotation.ListValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 校验分组-自定义校验器
 * <p>
 * <b>列表值校验器，校验指定范围中的值</b>
 *
 * @author liuwenjing
 * @date 2021年4月21日10:11:06
 */
public class ListValueValidator implements ConstraintValidator<ListValue, Integer> {

    /**
     * 前置Set
     */
    private final Set<Integer> valueSet = new LinkedHashSet<>();

    /**
     * 初始化方法
     *
     * @param constraintAnnotation 自定义校验注解
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.value();
        if (values.length > 0) {
            for (int value : values) {
                valueSet.add(value);
            }
        }
    }

    /**
     * 判断校验是否成功
     *
     * @param value   要校验值
     * @param context 在应用给定的约束验证器时提供上下文数据和操作
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return valueSet.contains(value);
    }
}
