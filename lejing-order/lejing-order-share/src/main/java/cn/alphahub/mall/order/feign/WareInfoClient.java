package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.ware.api.WareInfoApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 库存 - 获取运费
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/05/24
 */
@FeignClient(value = AppConstant.WARE_SERVICE, contextId = "wareInfoClient")
public interface WareInfoClient extends WareInfoApi {

}
