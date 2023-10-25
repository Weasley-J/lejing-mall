package cn.alphahub.mall.common.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * 排序
 *
 * @author liuwenjing
 */
@Getter
@Setter
@NoArgsConstructor
public final class SortArg implements Serializable {
    /**
     * 排序字段
     */
    private String column;
    /**
     * 是否升序
     */
    private Boolean isDesc;


    public <T> SortArg(ColumnFunction<T, Object> columnFunction, Boolean isDesc) {
        this.column = toBeanPropertyName(columnFunction);
        this.isDesc = isDesc;
    }

    public static <T> SortArg newSortArg(ColumnFunction<T, Object> columnFunction, Boolean isDesc) {
        return new SortArg(columnFunction, isDesc);
    }

    @SuppressWarnings("all")
    private String methodToProperty(String methodName) {
        if (methodName == null) {
            return null;
        }
        if (methodName.startsWith("is")) {
            methodName = methodName.substring(2);
        } else {
            if (methodName.startsWith("get") || methodName.startsWith("set")) {
                methodName = methodName.substring(3);
            } else {
                throw new RuntimeException("Error parsing toBeanPropertyName name '" + methodName + "'.  Didn't start with 'is', 'get' or 'set'.");
            }
        }
        if (methodName.length() == 1 || (methodName.length() > 1 && !Character.isUpperCase(methodName.charAt(1)))) {
            methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
        }
        return methodName;
    }

    @SuppressWarnings("all")
    private <T> String toBeanPropertyName(ColumnFunction<T, Object> columnFunction) {
        try {
            Method writeReplace = columnFunction.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            Object invokeObj = writeReplace.invoke(columnFunction);
            SerializedLambda serializedLambda = (SerializedLambda) invokeObj;
            String implMethodName = serializedLambda.getImplMethodName();
            String lambdaImplClass = serializedLambda.getImplClass();
            return methodToProperty(Objects.requireNonNull(implMethodName));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Failed to get java bean toBeanPropertyName name.");
        }
    }

    /**
     * Column Function
     *
     * @author liuwenjing
     */
    @FunctionalInterface
    public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {

    }
}
