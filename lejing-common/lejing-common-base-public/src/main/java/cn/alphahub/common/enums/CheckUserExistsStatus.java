package cn.alphahub.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 校验会员用户存在状态-枚举
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
@AllArgsConstructor
public enum CheckUserExistsStatus {
    /**
     * 15016 该用户名已注册过
     */
    USERNAME_EXISTS(15016, "该用户名已注册过"),
    /**
     * 15017 该邮箱已注册过
     */
    EMAIL_EXISTS(15017, "该邮箱已注册过"),
    /**
     * 15018 该手机号已注册过
     */
    PHONE_EXISTS(15018, "该手机号已注册过"),
    /**
     * 15019 该用户可以注册,用户不存在
     */
    USER_CAN_REGISTER(15019, "该用户可以注册,用户不存在"),
    /**
     * 15020 用户信息为空
     */
    USER_IS_EMPTY(15020, "用户信息为空");

    /**
     * 枚举值
     */
    @Getter
    @Setter
    private Integer value;

    /**
     * 枚举名
     */
    @Getter
    @Setter
    private String name;
}
