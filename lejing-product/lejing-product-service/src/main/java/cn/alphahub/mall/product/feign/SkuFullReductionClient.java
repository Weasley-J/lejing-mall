package cn.alphahub.mall.product.feign;

import cn.alphahub.mall.coupon.api.SkuFullReductionApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 秒杀活动商品关联-feign远程调用客户端
 *
 * @author liuwenjing
 */
@FeignClient(value = "lejing-coupon", contextId = "skuFullReductionClient")
public interface SkuFullReductionClient extends SkuFullReductionApi {

}
