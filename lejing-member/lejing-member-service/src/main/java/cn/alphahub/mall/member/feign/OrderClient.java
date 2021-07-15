package cn.alphahub.mall.member.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.order.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 订单 - feign客户端
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/06/27
 */
@FeignClient(value = AppConstant.ORDER_SERVICE, contextId = "orderClient")
public interface OrderClient extends OrderApi {
}
