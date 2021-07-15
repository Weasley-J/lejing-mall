package cn.alphahub.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码请求来源-枚举
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
@AllArgsConstructor
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
     * 来源名称所对应的值
     */
    @Getter
    @Setter
    private Integer value;

    /**
     * 来源名称
     */
    @Getter
    @Setter
    private String name;

}
