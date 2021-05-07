package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.ware.api.WareSkuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * sku库存
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/05/07
 */
@FeignClient(value = AppConstant.WARE_SERVICE, contextId = "wareSkuClient")
public interface WareSkuClient extends WareSkuApi {
}
