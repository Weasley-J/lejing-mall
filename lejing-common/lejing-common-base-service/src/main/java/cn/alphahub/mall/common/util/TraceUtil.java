package cn.alphahub.mall.common.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Trace Id工具类
 *
 * @author weasley
 * @version 1.0
 * @date 2022/7/1
 */
public class TraceUtil {

    private TraceUtil() {
    }

    /**
     * @return 返回8位的随机字符串（只包含数字和字符)
     */
    public static String getTraceId() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
