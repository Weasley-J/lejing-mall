package cn.alphahub.mall.common.config;

import cn.alphahub.mall.common.util.TraceUtil;
import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

import static cn.alphahub.mall.common.constant.FrameworkConstant.TRACE_ID;

/**
 * Feign请求模板增强配置类
 * <ul>
 *    <li>传递traceId到下游服务</li>
 *    <li>同步客户端携带过来的所有请求头信息</li>
 * </ul>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/05/01
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class FeignRequestConfig {
    /**
     * 传递Controller过来的请求头到Feign内部
     */
    public static final ThreadLocal<Map<String, String>> HEADER_MAP = new ThreadLocal<>();

    /**
     * Feign请求拦截器
     * <ul>
     *     <li>Feign远程调用前先进入RequestInterceptor.apply()方法</li>
     *     <li>RequestContextHolder获取当前请求的上下文, 基于ThreadLocal存储RequestContext</li>
     * </ul>
     *
     * @return 同步客户端请求头后的Feign请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                HttpServletRequest request = requestAttributes.getRequest();
                String originalHeader = request.getHeader(HttpHeaders.COOKIE);
                requestTemplate.header(HttpHeaders.COOKIE, originalHeader);
                if (null == request.getHeader(TRACE_ID)) {
                    String traceId;
                    if (StringUtils.isBlank(MDC.get(TRACE_ID))) {
                        traceId = TraceUtil.getTraceId();
                        MDC.put(TRACE_ID, traceId);
                    } else traceId = MDC.get(TRACE_ID);
                    requestTemplate.header(TRACE_ID, traceId);
                }
                if (null != HEADER_MAP.get()) {
                    for (Map.Entry<String, String> entry : HEADER_MAP.get().entrySet()) {
                        requestTemplate.header(entry.getKey(), entry.getValue());
                    }
                    HEADER_MAP.remove();
                }
            }
        };
    }

    /**
     * <p>还需要在配置Feign Client的日志级别为debug
     *
     * @return Log the headers, body, and metadata for both requests and responses.
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
