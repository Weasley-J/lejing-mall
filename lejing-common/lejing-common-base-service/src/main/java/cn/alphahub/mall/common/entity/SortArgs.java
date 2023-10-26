package cn.alphahub.mall.common.entity;

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
 * String orderBy = SortArgs.getOrderBy(
 *     SortArgs.newSortArg(Order::getPromotionAmount, false, "f_"),
 *     SortArgs.newSortArg(Order::getReceiverPostCode, true, "t_"),
 *     SortArgs.newSortArg(Order::getDeliveryCompany, true, null)
 *  );
 * }</pre>
 * <p>上述示例的结果将是: "f_promotion_amount ASC, t_receiver_post_code DESC, delivery_company DESC".</p>
 *
 * @author weasley
 * @version 1.0.0
 */
public class SortArgs implements Serializable {
    /**
     * 排序规则集合
     */
    private List<SortArg> sortArgs;

    /**
     * 私有构造方法
     *
     * @param sortArgs 排序参数
     */
    private SortArgs(List<SortArg> sortArgs) {
        this.sortArgs = sortArgs;
    }

    /**
     * 私有构造方法
     *
     * @param sortArgs 多个排序参数，可变参
     */
    private SortArgs(SortArg... sortArgs) {
        this.sortArgs = Arrays.asList(sortArgs);
    }

    /**
     * 静态方法创建 SortArgs 实例
     *
     * @param sortArgs 多个排序条件，可变参
     * @return 排序参数模型
     */
    public static SortArgs newSortArgs(SortArg... sortArgs) {
        return new SortArgs(sortArgs);
    }

    /**
     * 静态方法创建 SortArgs 实例
     *
     * @param sortArgs 排序条件列表
     * @return 排序参数模型
     */
    public static SortArgs newSortArgs(List<SortArg> sortArgs) {
        return new SortArgs(sortArgs);
    }

    /**
     * 获取排序条件，e.g: t_station_name DESC,t_cooperated_before ASC
     */
    public static String getOrderBy(List<SortArg> sortArgs) {
        if (null == sortArgs || sortArgs.isEmpty()) {
            return null;
        }
        StringBuilder orderBy = new StringBuilder();
        for (SortArg sortArg : sortArgs) {
            if (StringUtils.isBlank(sortArg.getColumn()) || !StringUtils.isCamel(sortArg.getColumn())) {
                continue;
            }
            String sortColumn = StringUtils.defaultIfBlank(sortArg.getColumnPrefix(), "") + StringUtils.camelToUnderline(sortArg.getColumn());
            String sortRule = sortArg.isDesc() ? "DESC" : "ASC";
            orderBy.append(sortColumn).append(" ").append(sortRule).append(", ");
        }
        if (orderBy.length() > 0) {
            orderBy.setLength(orderBy.length() - 2);
        }
        return StringUtils.defaultIfBlank(orderBy.toString(), null);
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
        return getOrderBy(sortArgs);
    }

    /**
     * The Column Function
     *
     * @author liuwenjing
     */
    @FunctionalInterface
    public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {
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
    }

    /**
     * StringUtils
     */
    public static class StringUtils {
        /**
         * 下划线字符
         */
        private static final char UNDERLINE = '_';
        private static final String UNDERSCORE = "_";
        private static final String EMPTY = "";

        private StringUtils() {
        }

        /**
         * 判断字符串中是否全是空白字符
         *
         * @param cs 需要判断的字符串
         * @return 如果字符串序列是 null 或者全是空白，返回 true
         */
        public static boolean isBlank(CharSequence cs) {
            if (cs != null) {
                int length = cs.length();
                for (int i = 0; i < length; i++) {
                    if (!Character.isWhitespace(cs.charAt(i))) {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
         * 判断字符串是不是驼峰命名
         *
         * <li> 包含 '_' 不算 </li>
         * <li> 首字母大写的不算 </li>
         *
         * @param str 字符串
         * @return 结果
         */
        public static boolean isCamel(String str) {
            return Character.isLowerCase(str.charAt(0)) && !str.contains(UNDERSCORE);
        }

        /**
         * <p>Returns either the passed in CharSequence, or if the CharSequence is
         * whitespace, empty ("") or {@code null}, the value of {@code defaultStr}.</p>
         *
         * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
         *
         * <pre>
         * StringUtils.defaultIfBlank(null, "NULL")  = "NULL"
         * StringUtils.defaultIfBlank("", "NULL")    = "NULL"
         * StringUtils.defaultIfBlank(" ", "NULL")   = "NULL"
         * StringUtils.defaultIfBlank("bat", "NULL") = "bat"
         * StringUtils.defaultIfBlank("", null)      = null
         * </pre>
         *
         * @param <T>        the specific kind of CharSequence
         * @param str        the CharSequence to check, may be null
         * @param defaultStr the default CharSequence to return
         *                   if the input is whitespace, empty ("") or {@code null}, may be null
         * @return the passed in CharSequence, or the default
         */
        public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
            return isBlank(str) ? defaultStr : str;
        }

        /**
         * 字符串驼峰转下划线格式
         *
         * @param param 需要转换的字符串
         * @return 转换好的字符串
         */
        public static String camelToUnderline(String param) {
            if (isBlank(param)) {
                return EMPTY;
            }
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c) && i > 0) {
                    sb.append(UNDERLINE);
                }
                sb.append(Character.toLowerCase(c));
            }
            return sb.toString();
        }
    }
}
