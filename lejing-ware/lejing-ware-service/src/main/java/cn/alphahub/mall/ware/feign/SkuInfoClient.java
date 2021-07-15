package cn.alphahub.mall.ware.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.SkuInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * sku信息 - feign客户端
 *
 * @author Weasley J
 */
@FeignClient(value = AppConstant.PRODUCT_SERVICE)
public interface SkuInfoClient extends SkuInfoApi {

}
