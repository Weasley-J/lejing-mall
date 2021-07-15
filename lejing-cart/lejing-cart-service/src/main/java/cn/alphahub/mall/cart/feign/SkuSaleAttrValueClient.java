package cn.alphahub.mall.cart.feign;

import cn.alphahub.mall.product.api.SkuSaleAttrValueApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * sku销售属性&值 - feign远程调用客户端
 *
 * @author liuwenjing
 * @date 2021年4月3日
 */
@FeignClient(value = "lejing-product", contextId = "skuSaleAttrValueClient")
public interface SkuSaleAttrValueClient extends SkuSaleAttrValueApi {
}
