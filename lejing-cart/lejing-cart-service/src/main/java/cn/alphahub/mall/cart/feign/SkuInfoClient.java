package cn.alphahub.mall.cart.feign;

import cn.alphahub.mall.product.api.SkuInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * SKU信息 - feign远程调用客户端
 *
 * @author liuwenjing
 * @date 2021年4月3日
 */
@FeignClient(value = "lejing-product", contextId = "skuInfoClient")
public interface SkuInfoClient extends SkuInfoApi {

}
