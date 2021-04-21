package cn.alphahub.mall.order.interceptor;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.to.UserInfoTo;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.order.constant.OrderConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * <b>登录拦截器</b>
 * <p>
 * <p>在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
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
            // 用户已登录
            Member member = (Member) attribute;
            userInfo.setUserId(member.getId());
            userInfoThreadLocal.set(userInfo);
            return true;
        } else {
            // 用户未登录重定向到登录链接
            response.sendRedirect(OrderConstant.LOGIN_SERVICE_URL);
            return false;
        }
    }

    /**
     * 目标方法之后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

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
