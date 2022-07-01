package cn.alphahub.mall.common.constant;

/**
 * 框架常量类
 *
 * @author weasley
 * @version 1.0
 * @date 2022/6/30
 */
public interface FrameworkConstant {
    String THIRD_HEADER_TOKEN_PREFIX = "third-";
    /**
     * TRACE ID
     */
    String TRACE_ID = "X-B3-TraceId";

    //只能/api的请求才能够通过网关
    String URL_API_PREFIX_API = "/api";
    String URL_API_PREFIX_RPC = "/rpc";

    //无需权限拦截
    String URL_API_PREFIX_PUBLIC = "/api/public";

    /**
     * 请求上下文用户ID
     */
    String REQUEST_CONTEXT_USER_ID = "X-TRACE-USER-CODE";
}
