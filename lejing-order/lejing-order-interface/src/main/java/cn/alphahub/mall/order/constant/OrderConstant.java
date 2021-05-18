package cn.alphahub.mall.order.constant;

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

}
