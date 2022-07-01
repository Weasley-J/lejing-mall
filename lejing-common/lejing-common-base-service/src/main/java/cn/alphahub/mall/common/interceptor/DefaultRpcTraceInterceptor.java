package cn.alphahub.mall.common.interceptor;

import cn.alphahub.mall.common.constant.FrameworkConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class DefaultRpcTraceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            MDC.put(FrameworkConstant.REQUEST_CONTEXT_USER_ID, request.getHeader(FrameworkConstant.REQUEST_CONTEXT_USER_ID));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.error("获取REQUEST_CONTEXT_USER_ID异常", e);
            }
        }
        return true;
    }

}
