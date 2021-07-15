package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.SkuInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * SKU信息 - feign远程调用客户端
 *
 * @author liuwenjing
 * @date 2021年4月30日
 */
@FeignClient(value = AppConstant.PRODUCT_SERVICE, contextId = "skuInfoClient")
public interface SkuInfoClient extends SkuInfoApi {

}
