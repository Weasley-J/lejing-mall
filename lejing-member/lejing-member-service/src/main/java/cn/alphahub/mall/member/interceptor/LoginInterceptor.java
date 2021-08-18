package cn.alphahub.mall.member.interceptor;

import cn.alphahub.common.constant.AuthConstant;
import cn.alphahub.common.enums.AppEnvironmentEnum;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.order.constant.OrderConstant;
import com.alibaba.nacos.common.utils.CollectionUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <b>登录拦截器</b>
 * <p>在执行目标方法之前，判断用户的登录状态.并封装传递给controller目标请求</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 允许URI放行的白名单，不需要登录，ant match pattern
     */
    public static final Set<String> URI_WHITELIST_ANT_MATCH_PATTERN = new LinkedHashSet<>(20);
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    /**
     * 保存用户信息的threadLocal变量
     */
    public static ThreadLocal<Member> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

    static {
        URI_WHITELIST_ANT_MATCH_PATTERN.add("/member/**");
    }

    @Resource
    private Environment environment;

    /**
     * 从线程的局部变量中获取用户信息
     *
     * @return User对象
     */
    public static Member getUserInfo() {
        return USER_INFO_THREAD_LOCAL.get();
    }

    /**
     * 目标方法之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CollectionUtils.isNotEmpty(Arrays.asList(environment.getActiveProfiles()))
                && AppEnvironmentEnum.getApiDocCanVisitEnv().contains(environment.getActiveProfiles()[0])) {
            if (Objects.equals(request.getRequestURI(), "/")) {
                return Boolean.TRUE;
            }
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            Set<String> uriWhitelist = new LinkedHashSet<>();
            uriWhitelist.add("/index");
            uriWhitelist.add("/index.html");
            uriWhitelist.add("/AllInOne.css");
            uriWhitelist.add("/search.js");
            uriWhitelist.add("/debug.js");
            uriWhitelist.add("/static/**");
            for (String uriPattern : uriWhitelist) {
                if (antPathMatcher.match(uriPattern, request.getRequestURI())) {
                    return Boolean.TRUE;
                }
            }
        }
        // 白名单放行
        String requestUri = request.getRequestURI();
        for (String uriPattern : URI_WHITELIST_ANT_MATCH_PATTERN) {
            if (ANT_PATH_MATCHER.match(uriPattern, requestUri)) {
                return true;
            }
        }

        // session
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(AuthConstant.LOGIN_USER);

        if (Objects.nonNull(attribute) && (attribute instanceof Member)) {
            // 用户已登录
            Member member = (Member) attribute;
            USER_INFO_THREAD_LOCAL.set(member);
            return true;
        } else {
            // 用户未登录，重定向到登录链接
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('请先进行登录，再进行后续操作！');location.href='" + OrderConstant.LOGIN_PAGE_URL + "'</script>");
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
        USER_INFO_THREAD_LOCAL.remove();
    }
}
