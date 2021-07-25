package cn.alphahub.mall.seckill.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.SkuInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * SKU信息 - feign远程调用客户端
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/07/25
 */
@FeignClient(name = AppConstant.PRODUCT_SERVICE, contextId = "skuInfoClient")
public interface SkuInfoClient extends SkuInfoApi {
}
