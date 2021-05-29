package cn.alphahub.mall.order.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
     * 订单状态枚举
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStatusEnum {
        /**
         * 待付款 0
         */
        CREATE_NEW("待付款", 0),
        /**
         * 已付款 1
         */
        PAID("已付款", 1),
        /**
         * 已发货-2
         */
        SEND("已发货", 2),
        /**
         * 已完成 3
         */
        RECEIVED("已完成", 3),
        /**
         * 已取消 4
         */
        CANCELLED("已取消", 4),
        /**
         * 售后中 5
         */
        SERVICING("售后中", 5),
        /**
         * 售后完成 6
         */
        SERVICED("售后完成", 6);

        /**
         * 枚举名称
         */
        private final String name;
        /**
         * 枚举值
         */
        private final Integer value;
    }
}
