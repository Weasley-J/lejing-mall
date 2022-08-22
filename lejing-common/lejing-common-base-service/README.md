# 全局埋traceId追踪线上问题快速指南

## 1 找到`lejing-mall`的[`logback-spring.xml`](https://github.com/Weasley-J/lejing-mall/blob/main/lejing-common/lejing-common-util/src/main/resources/logback-spring.xml)粘贴复制到你的项目

提示：`LOGSTASH`相关的配置打开即可

## 2 修改`RestControllerAdvice`的`basePackage`的值

位置：cn.alphahub.mall.common.advice.ResultResponseBodyAdvice

```java
import cn.alphahub.mall.common.EscapeResult;
import cn.alphahub.mall.common.constant.BizConstant;
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
@RestControllerAdvice(value = {BizConstant.basePackage})
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
```

## 3 需要traceId的返回结果包装使用`cn.alphahub.mall.common.core.domain.Result`返回数据

## 4 不需traceId的返回结果包装写个类实现`cn.alphahub.mall.common.EscapeResult`此类，返回结果就不会添加traceId

适用场景：三方对接的情景

## 5 接下来对于同一个线程的log所打印的日志都会获得同一个traceId，到日志系统内可以快速定位代码行数找到问题

以ELK日志系统为例：

- 访问`lejing-third-party`接口`http://localhost:30000/oss/policy`，观察接口返回：

```json
{
  "traceId": "vErQFPgR",
  "message": "操作成功",
  "success": true,
  "timestamp": "2022-08-05 14:07:54",
  "code": 200,
  "data": {
    "accessId": "LTAI4GFKKZSC4w9gCLfTcAZY",
    "policy": "eyJleHBpcmF0aW9uIjoiMjAyMi0wOC0wNVQwNjowODoyNC44MjhaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCIyMDIyLTA4LTA1Il1dfQ==",
    "signature": "5lsVyfkzim+874+Y8Jnb42a4LLg=",
    "dir": "2022-08-05",
    "host": "https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com",
    "expire": "1659679704",
    "callbackUrl": null
  }
}
```

控制台：

![image-20220805140902495](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20220805140902495.png)

- 去ELK内查询`"traceId": "vErQFPgR",`对应的日志

![image-20220805140957989](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20220805140957989.png)

![image-20220805141123220](https://alphahub-test-bucket.oss-cn-shanghai.aliyuncs.com/image/image-20220805141123220.png)

