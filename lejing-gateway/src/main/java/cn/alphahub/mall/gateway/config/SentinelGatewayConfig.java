package cn.alphahub.mall.gateway.config;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.enums.BizCodeEnum;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 自定义SentinelGateway阻塞返回方法
 *
 * @author liuwenjing
 * @version 1.0.8
 */
@Component
public class SentinelGatewayConfig {

    public SentinelGatewayConfig() {
        GatewayCallbackManager.setBlockHandler((serverWebExchange, throwable) -> {
            /* 网关限流了请求，就会调用此回调 */
            String errJson = JSON.toJSONString(BaseResult.error(BizCodeEnum.TO_MANY_REQUEST.getCode(), BizCodeEnum.TO_MANY_REQUEST.getMessage()));
            return ServerResponse.ok().body(Mono.just(errJson), String.class);
        });
    }
}
