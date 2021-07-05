package cn.alphahub.common.constant;

/**
 * <b>购物车常量</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
public class CartConstant {

    /**
     * 购物车cookie名
     */
    public final static String TEMP_USER_COOKIE_NAME = "user-key";

    /**
     * 购物车超时设置
     */
    public final static int TEMP_USER_COOKIE_TIMEOUT = 60 * 60 * 24 * 2;

    /**
     * 购物车Redis数据key前缀
     */
    public final static String CART_PREFIX = "lejing:cart:";
}
