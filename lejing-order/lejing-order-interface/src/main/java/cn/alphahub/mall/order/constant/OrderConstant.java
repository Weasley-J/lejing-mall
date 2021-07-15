package cn.alphahub.mall.order.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * <b>订单常量类</b>
 * <p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/21
 */
public class OrderConstant {
    /**
     * 用户登录URL链接
     */
    public static final String LOGIN_PAGE_URL = "http://auth.lejing.com/login.html";

    /**
     * redis用户订单确认页防重复令牌
     */
    public static final String USER_ORDER_CONFIRM_TOKEN = "order:confirm:token:";

    /**
     * 去订单结算确认页URL
     */
    public static final String TO_TRADE_URL = "http://order.lejing.com/toTrade";

    /**
     * 人民币最小单位：￥0.01元
     */
    public static final BigDecimal RMB_MIN_UNIT = new BigDecimal("0.01");

    /**
     * 订单状态枚举
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStatusEnum {
        /**
         * 待付款 0
         */
        CREATE_NEW(0, "待付款"),
        /**
         * 已付款 1
         */
        PAID(1, "已付款"),
        /**
         * 已发货-2
         */
        SEND(2, "已发货"),
        /**
         * 已完成 3
         */
        RECEIVED(3, "已完成"),
        /**
         * 已取消 4
         */
        CANCELLED(4, "已取消"),
        /**
         * 售后中 5
         */
        SERVICING(5, "售后中"),
        /**
         * 售后完成 6
         */
        SERVICED(6, "售后完成");
        /**
         * 枚举值
         */
        private final Integer value;

        /**
         * 枚举名称
         */
        private final String name;
    }

    /**
     * 支付方式枚举常量
     */
    @Getter
    @AllArgsConstructor
    public enum PayTypeEnum {
        /**
         * 1 支付宝支付
         */
        ALIPAY(1, "支付宝支付"),
        /**
         * 2 微信支付
         */
        WECHAT_PAY(2, "微信支付"),
        /**
         * 3 银联支付
         */
        UNION_PAY(3, "银联支付"),
        /**
         * 4 货到付款
         */
        CASH_ON_DELIVERY(4, "货到付款");

        /**
         * 枚举值
         */
        private final Integer value;
        /**
         * 枚举名称
         */
        private final String name;
    }
}
