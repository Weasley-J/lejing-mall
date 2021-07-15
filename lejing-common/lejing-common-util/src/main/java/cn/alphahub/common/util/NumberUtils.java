package cn.alphahub.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HuYi.Zhang
 * @date 2018-04-25 09:13
 **/
public class NumberUtils {

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判断字符串是否是数值格式
     *
     * @param str 判断的字符串
     * @return true|false
     */
    public static boolean isDigit(String str) {
        if (str == null || "".equals(str.trim())) {
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 将一个小数精确到指定位数
     *
     * @param num   要精确的数
     * @param scale 精确多少位
     * @return 精确的数
     */
    public static double scale(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 从字符串中根据正则表达式寻找，返回找到的数字数组
     *
     * @param value 字符串
     * @param regex 正则表达式
     * @return 符合正则表达式数字数组
     */
    public static Double[] searchNumber(String value, String regex) {
        List<Double> doubles = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if (matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            for (int i = 1; i <= result.groupCount(); i++) {
                doubles.add(Double.valueOf(result.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }

    /**
     * 生成指定位数的随机数字
     *
     * @param length 验证码的长度，如：123456，6位
     * @return 验证码
     */
    public static String generateCode(int length) {
        length = Math.min(length, 8);
        int min = Double.valueOf(Math.pow(10, length - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, length + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0, length);
    }
}
