package cn.alphahub.mall.common.entity;

import cn.alphahub.mall.common.entity.SortArgs.SortArg.ColumnFunction;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

/**
 * 函数式排序模型
 * <p>
 * 此类提供了一种函数式创建和处理查询排序条件的方法。
 * <p>用法示例 1:</p>
 * <pre>{@code
 * String orderBy = SortArgs.getOrderBy(new ArrayList<SortArg>() {{
 *     add(SortArgs.newSortArg(Order::getUseIntegration, true, null));
 *     add(SortArgs.newSortArg(Order::getCommentTime, true, null));
 *     add(SortArgs.newSortArg(Order::getDiscountAmount, false, null));
 *     add(SortArgs.newSortArg(Order::getReceiverDetailAddress, false, "t_"));
 * }});
 * }</pre>
 *
 * <p>上述示例的结果将是: "use_integration DESC, comment_time DESC, discount_amount ASC, t_receiver_detail_address ASC".</p>
 * <p>用法示例 2:</p>
 * <pre>{@code
 * String orderBy = SortArgs.getOrderBy(SortArgs.newSortArg(Order::getPromotionAmount, false, "f_"),
 *     SortArgs.newSortArg(Order::getReceiverPostCode, true, "t_"),
 *     SortArgs.newSortArg(Order::getDeliveryCompany, true, null)
 *  );
 * }</pre>
 * <p>上述示例的结果将是: "f_promotion_amount ASC, t_receiver_post_code DESC, delivery_company DESC".</p>
 *
 * @author weasley
 * @version 1.0.0
 */
@SuppressWarnings({"all"})
public class SortArgs implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(SortArgs.class);
    /**
     * 排序规则集合
     */
    private List<SortArg> sortArgs;

    private SortArgs(List<SortArg> sortArgs) {
        this.sortArgs = sortArgs;
    }

    private SortArgs(SortArg... sortArgs) {
        this.sortArgs = Arrays.asList(sortArgs);
    }

    public static SortArgs newSortArgs(SortArg... sortArgs) {
        return new SortArgs(sortArgs);
    }

    public static SortArgs newSortArgs(List<SortArg> sortArgs) {
        return new SortArgs(sortArgs);
    }

    /**
     * 获取排序条件，e.g: t_station_name DESC,t_cooperated_before ASC
     */
    public static String getOrderBy(List<SortArg> sortArgs) {
        if (CollectionUtils.isEmpty(sortArgs)) {
            return null;
        }
        StringBuilder orderBy = new StringBuilder();
        for (SortArg sortArg : sortArgs) {
            if (StringUtils.isBlank(sortArg.getColumn()) || !StringUtils.isCamel(sortArg.getColumn())) {
                continue;
            }
            String sortColumn = org.apache.commons.lang3.StringUtils.defaultIfBlank(sortArg.getColumnPrefix(), "") + StringUtils.camelToUnderline(sortArg.getColumn());
            String sortRule = sortArg.isDesc() ? "DESC" : "ASC";
            orderBy.append(sortColumn).append(" ").append(sortRule).append(", ");
        }
        if (orderBy.length() > 0) {
            orderBy.setLength(orderBy.length() - 2);
        }
        return org.apache.commons.lang3.StringUtils.defaultIfBlank(orderBy.toString(), null);
    }

    /**
     * 获取排序条件，e.g: t_station_name DESC,t_cooperated_before ASC
     */
    public static String getOrderBy(SortArg... sortArgs) {
        List<SortArg> argList = Arrays.asList(sortArgs);
        return getOrderBy(argList);
    }

    /**
     * 用于创建 SortArg 实例的静态方法
     */
    public static <T> SortArg newSortArg(ColumnFunction<T, Object> columnFunction, Boolean isDesc, String columnPrefix) {
        return new SortArg(columnFunction, isDesc, columnPrefix);
    }

    public List<SortArg> getSortArgs() {
        return sortArgs;
    }

    public void setSortArgs(List<SortArg> sortArgs) {
        this.sortArgs = sortArgs;
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
     * 排序模型
     *
     * @author liuwenjing
     */
    public static final class SortArg implements Serializable {
        /**
         * 排序字段
         */
        private String column;
        /**
         * 是否升序
         */
        private boolean isDesc;
        /**
         * 列前缀
         */
        private String columnPrefix;

        public <T> SortArg(ColumnFunction<T, Object> columnFunction, boolean isDesc, String columnPrefix) {
            this.column = getPropertyName(columnFunction);
            this.isDesc = isDesc;
            this.columnPrefix = columnPrefix;
        }

        @SuppressWarnings({"all"})
        private String getPropertyNameFromMethodName(String methodName) {
            if (methodName == null) {
                return null;
            }
            if (methodName.startsWith("is")) {
                methodName = methodName.substring(2);
            } else {
                if (methodName.startsWith("get") || methodName.startsWith("set")) {
                    methodName = methodName.substring(3);
                } else {
                    throw new RuntimeException("Error parsing getPropertyName name '" + methodName + "'.  Didn't start with 'is', 'get' or 'set'.");
                }
            }
            if (methodName.length() == 1 || (methodName.length() > 1 && !Character.isUpperCase(methodName.charAt(1)))) {
                methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
            }
            return methodName;
        }

        @SuppressWarnings({"all"})
        private <T> String getPropertyName(ColumnFunction<T, Object> columnFunction) {
            try {
                Method writeReplace = columnFunction.getClass().getDeclaredMethod("writeReplace");
                writeReplace.setAccessible(true);
                Object invokeObj = writeReplace.invoke(columnFunction);
                SerializedLambda lambda = (SerializedLambda) Objects.requireNonNull(invokeObj);
                String implMethodName = lambda.getImplMethodName();
                return getPropertyNameFromMethodName(implMethodName);
            } catch (Exception e) {
                throw new RuntimeException("Failed to get java bean property name.", e);
            }
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public boolean isDesc() {
            return isDesc;
        }

        public void setDesc(boolean desc) {
            isDesc = desc;
        }

        public String getColumnPrefix() {
            return columnPrefix;
        }

        public void setColumnPrefix(String columnPrefix) {
            this.columnPrefix = columnPrefix;
        }

        /**
         * The Column Function
         *
         * @author liuwenjing
         */
        @FunctionalInterface
        public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {
        }
    }
}
