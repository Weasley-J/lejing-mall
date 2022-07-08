package cn.alphahub.mall.common.advice;

import cn.alphahub.mall.common.EscapeResult;
import cn.alphahub.mall.common.constant.FrameworkConstant;
import cn.alphahub.mall.common.core.abstraction.AbstractResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.common.entity.EscapeResultWrapper;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * traceId处理切面类
 *
 * @author weasley
 * @version 1.0
 * @date 2022/7/1
 */
@RestControllerAdvice({"cn.alphahub"})
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultResponseBodyAdvice.class);

    /**
     * Whether this component supports the given controller method return type
     * and the selected {@code HttpMessageConverter} type.
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * Invoked after an {@code HttpMessageConverter} is selected and just before
     * its write method is invoked.
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String traceId = MDC.get(FrameworkConstant.TRACE_ID);

        if (null == body) {
            Result<Object> result = Result.ok();
            result.setTraceId(traceId);
            return result;
        }

        if (body instanceof EscapeResultWrapper) {
            return ((EscapeResultWrapper<?>) body).getData();
        }

        if (body instanceof EscapeResult) {
            return body;
        }

        if ((body instanceof AbstractResult)) {
            if (((AbstractResult<?>) body).getTraceId() == null) {
                ((AbstractResult<?>) body).setTraceId(traceId);
                return body;
            }
        } else {
            Result<Object> result = Result.ok(body);
            result.setTraceId(traceId);
            if (body instanceof String) {
                result.setMessage(null);
                return JSONUtil.toJsonStr(result);
            }
            return result;
        }

        return body;
    }
}
