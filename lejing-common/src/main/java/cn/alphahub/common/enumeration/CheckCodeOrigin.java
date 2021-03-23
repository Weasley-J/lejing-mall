package cn.alphahub.common.enumeration;

import lombok.Getter;

/**
 * 验证码请求来源-枚举
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
@Getter
public enum CheckCodeOrigin {
    /**
     * 1 - 使用移动端请求验证码
     */
    MOBILE(1, "移动端请求验证码"),
    /**
     * 2 - 使用浏览器请求验证码
     */
    BROWSER(2, "浏览器请求验证码"),
    /**
     * 0 - 未知来源
     */
    UNKNOWN(0, "未知来源");

    /**
     * 来源名称
     */
    private final String name;

    /**
     * 来源名称所对应的值
     */
    private final Integer value;

    CheckCodeOrigin(Integer value, String name) {
        this.name = name;
        this.value = value;
    }
}
