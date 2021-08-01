package cn.alphahub.common.constant;

/**
 * <b>授权常量类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/23
 */
public class AuthConstant {
    /**
     * Redis缓存的验证码key前缀
     */
    public static final String REDIS_KEY_PREFIX = "user:verify:";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "loginUser";
    /**
     * 用户登录URL链接
     */
    public static final String LOGIN_PAGE_URL = "http://auth.lejing.com/login.html";
}
