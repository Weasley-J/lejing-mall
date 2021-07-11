package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.cart.api.CartApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 购物车 - feign客户端
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/30
 */
@FeignClient(value = AppConstant.CART_SERVICE, contextId = "cartClient")
public interface CartClient extends CartApi {

}
