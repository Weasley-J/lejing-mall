package cn.alphahub.mall.common.base.interceptor;

import cn.alphahub.mall.common.base.constant.FrameworkConstant;
import cn.hutool.core.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateTraceInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String traceId = StringUtils.defaultString(MDC.get(FrameworkConstant.TRACE_ID), RandomUtil.randomString(8));
        request.getHeaders().set(FrameworkConstant.TRACE_ID, traceId);
        return clientHttpRequestExecution.execute(request, body);
    }

}
