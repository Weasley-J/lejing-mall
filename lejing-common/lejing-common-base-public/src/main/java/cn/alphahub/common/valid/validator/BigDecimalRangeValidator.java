package cn.alphahub.common.valid.validator;


import cn.alphahub.common.valid.annotation.DecimalRange;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 检查正在验证的高精度数字是否在指定值域内:[min,max]
 *
 * @author liuwenjing
 * @date 2021年4月21日14:00:34
 */
public class BigDecimalRangeValidator implements ConstraintValidator<DecimalRange, BigDecimal> {
    /**
     * 最小值：key
     */
    private static final String MIN = "min";
    /**
     * 最大值：key
     */
    private static final String MAX = "max";

    /**
     * 前置Map
     */
    private final Map<String, BigDecimal> valueMap = new LinkedHashMap<>(2);

    @Override
    public void initialize(DecimalRange valueRange) {
        valueMap.put(MIN, new BigDecimal(valueRange.min()));
        valueMap.put(MAX, new BigDecimal(valueRange.max()));
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value.compareTo(valueMap.get(MIN)) >= 0 && value.compareTo(valueMap.get(MAX)) <= 0;
    }
}
