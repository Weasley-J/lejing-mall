package cn.alphahub.mall.common.base.util;

import cn.hutool.core.util.RandomUtil;

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
        return RandomUtil.randomString(8);
    }
}
