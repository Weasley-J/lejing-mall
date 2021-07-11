package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.SpuInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * SPU信息 - feign远程调用客户端
 *
 * @author lwj
 * @version 1.0
 * @date 2021-06-03 16:23
 */
@FeignClient(value = AppConstant.PRODUCT_SERVICE, contextId = "spuInfoClient")
public interface SpuInfoClient extends SpuInfoApi {

}
