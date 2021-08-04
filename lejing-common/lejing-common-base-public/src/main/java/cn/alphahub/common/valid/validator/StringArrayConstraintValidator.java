package cn.alphahub.common.valid.validator;

import cn.alphahub.common.valid.annotation.IncludeString;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 检查被校验的<b style="color: red">值</b>是否在期望的字符数组内
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/04
 */
public class StringArrayConstraintValidator implements ConstraintValidator<IncludeString, String> {
    /**
     * 前置Set
     */
    private Set<String> stringSet = new LinkedHashSet<>();

    @Override
    public void initialize(IncludeString includeString) {
        ConstraintValidator.super.initialize(includeString);
        if (includeString.values().length > 0) {
            stringSet = Arrays.stream(includeString.values()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return stringSet.contains(value);
    }
}
