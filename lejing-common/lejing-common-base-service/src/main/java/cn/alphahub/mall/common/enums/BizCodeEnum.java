package cn.alphahub.mall.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <b>错误码和错误信息枚举类</b>
 * <br>
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * <br>
 * 错误码列表：
 * 10: 通用
 * 001：参数格式校验
 * 11: 商品
 * 12: 订单
 * 13: 购物车
 * 14: 物流
 *
 * @author liuwenjing
 */
@Getter
@AllArgsConstructor
public enum BizCodeEnum {
    /**
     * 系统未知异常
     */
    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    /**
     * 参数格式校验异常
     */
    VALID_EXCEPTION(10001, "参数格式校验异常"),
    /**
     * 请求流量过大，请稍后再试
     */
    TO_MANY_REQUEST(10002, "请求流量过大，请稍后再试"),
    /**
     * 验证码获取频率太高，请稍后再试
     */
    SMS_CODE_EXCEPTION(10003, "验证码获取频率太高，请稍后再试"),
    /**
     * 商品上架异常
     */
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),
    /**
     * 存在相同的用户
     */
    USER_EXIST_EXCEPTION(15001, "存在相同的用户"),
    /**
     * 存在相同的手机号
     */
    PHONE_EXIST_EXCEPTION(15002, "存在相同的手机号"),
    /**
     * 商品库存不足
     */
    NO_STOCK_EXCEPTION(21000, "商品库存不足"),
    /**
     * 账号或密码错误
     */
    LOGIN_ACCOUNT_PASSWORD_EXCEPTION(15003, "账号或密码错误"),
    ;
    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;
}
