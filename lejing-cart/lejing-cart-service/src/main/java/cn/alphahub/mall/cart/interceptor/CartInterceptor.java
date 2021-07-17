package cn.alphahub.mall.cart.interceptor;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.constant.CartConstant;
import cn.alphahub.common.to.UserInfoTo;
import cn.alphahub.mall.member.domain.Member;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * <b>购物车拦截器</b>
 * <p>在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
@Component
public class CartInterceptor implements HandlerInterceptor {
    /**
     * 保存用户信息的threadLocal变量
     */
    public static ThreadLocal<UserInfoTo> userInfoThreadLocal = new ThreadLocal<>();

    /**
     * 从线程的局部变量中获取用户信息
     *
     * @return User对象
     */
    public static UserInfoTo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    /**
     * 目标方法之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserInfoTo userInfo = new UserInfoTo();
        // session
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(AuthConstant.LOGIN_USER);
        if (attribute instanceof Member) {
            //已登录
            Member member = (Member) attribute;
            userInfo.setUserId(member.getId());
        }
        // cookie
        Cookie[] cookies = request.getCookies();
        if (CollectionUtils.isNotEmpty(Arrays.asList(cookies))) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                // cookie: user-key
                if (CartConstant.TEMP_USER_COOKIE_NAME.equals(cookieName)) {
                    userInfo.setUserKey(cookieValue);
                    //标记为非临时用户
                    userInfo.setTempUser(Boolean.FALSE);
                } else {
                    //标记为临时用户
                    userInfo.setTempUser(Boolean.TRUE);
                }
            }
        }
        if (ObjectUtils.isNull(userInfo.getUserKey())) {
            userInfo.setUserKey(IdUtil.fastSimpleUUID());
        }
        userInfoThreadLocal.set(userInfo);
        return true;
    }

    /**
     * 目标方法之后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfo = userInfoThreadLocal.get();
        String userKey = userInfo.getUserKey();
        // 目标方法之后给浏览器设置Cookie
        if (userInfo.getTempUser()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userKey);
            // this sensitive cookie is protected against theft
            cookie.setHttpOnly(true);
            cookie.setDomain("lejing.com");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }
    }

    /**
     * 目标方法之执行完成后的回调
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //标方法之执行完成后的清空线程的局部变量
        userInfoThreadLocal.remove();
    }
}
