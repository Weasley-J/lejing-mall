package cn.alphahub.mall.common.entity;

import cn.alphahub.mall.common.entity.SortArgs.SortArg.ColumnFunction;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * 排序
 *
 * @author weasley
 * @version 1.0.0
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SortArgs implements Serializable {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";
    /**
     * 排序规则映射
     */
    private List<SortArg> sortArgs;

    /**
     * 获取排序条件，示例: t_station_name DESC,t_cooperated_before ASC
     */
    public static String getOrderBy(List<SortArg> sortArgs) {
        if (CollectionUtils.isEmpty(sortArgs)) {
            return null;
        }
        StringBuilder orderBy = new StringBuilder();
        for (SortArg sortArg : sortArgs) {
            if (StringUtils.isBlank(sortArg.getColumn())) {
                continue;
            }
            if (!StringUtils.isCamel(sortArg.getColumn())) {
                continue;
            }
            String columnPrefix = org.apache.commons.lang3.StringUtils.defaultIfBlank(sortArg.getColumnPrefix(), "");
            String sortColumn = columnPrefix + StringUtils.camelToUnderline(sortArg.getColumn());
            String sortRule = (null != sortArg.getIsDesc() && sortArg.getIsDesc()) ? DESC : ASC;
            orderBy.append(sortColumn).append(" ").append(sortRule).append(", ");
        }
        if (orderBy.length() > 0) {
            orderBy.deleteCharAt(orderBy.length() - 2);
        }
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(orderBy.toString(), null);
    }

    /**
     * A Static method for creating SortArg Instance
     */
    public static <T> SortArg newSortArg(ColumnFunction<T, Object> columnFunction, Boolean isDesc, String columnPrefix) {
        return new SortArg(columnFunction, isDesc, columnPrefix);
    }

    /**
     * 获取排序条件，示例: t_station_name DESC,t_cooperated_before ASC
     */
    public String getOrderBy() {
        String orderBy = getOrderBy(sortArgs);
        log.info("排序规则: {}", orderBy);
        return orderBy;
    }

    /**
     * 排序
     *
     * @author liuwenjing
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static final class SortArg implements Serializable {
        /**
         * 排序字段
         */
        private String column;
        /**
         * 是否升序
         */
        private Boolean isDesc;
        /**
         * 表前缀
         */
        private String columnPrefix;

        public <T> SortArg(ColumnFunction<T, Object> columnFunction, Boolean isDesc, String columnPrefix) {
            this.column = toBeanPropertyName(columnFunction);
            this.isDesc = isDesc;
            this.columnPrefix = columnPrefix;
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
}
