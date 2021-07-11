package cn.alphahub.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 验证码发送结果-枚举
 *
 * @author liuwenjing
 * @date 2021年3月23日
 */
@AllArgsConstructor
public enum CheckCodeStatus {
    /**
     * 10001：手机号为空
     */
    EMPTY_PHONE(10001, "手机号不能为空"),
    /**
     * 10002 - 请稍后再试
     */
    SEND(10002, "请稍后再试"),
    /**
     * 10003 - 发送成功
     */
    SUCCESS(10003, "发送成功"),
    /**
     * 10004 - 发送错误
     */
    ERROR(10004, "发送错误");

    /**
     * 枚举值
     */
    @Getter
    @Setter
    private Integer value;

    /**
     * 枚举名称
     */
    @Getter
    @Setter
    private String name;

}
