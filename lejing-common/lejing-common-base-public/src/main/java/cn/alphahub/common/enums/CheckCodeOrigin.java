package cn.alphahub.common.enums;

/**
 * 验证码请求来源-枚举
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
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
    private String name;

    /**
     * 来源名称所对应的值
     */
    private Integer value;

    CheckCodeOrigin(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
