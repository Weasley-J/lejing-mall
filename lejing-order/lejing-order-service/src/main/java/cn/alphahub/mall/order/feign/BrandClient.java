package cn.alphahub.mall.order.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 品牌信息 - feign远程调用客户端
 * <p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-06-03 16:23
 */
@FeignClient(value = AppConstant.PRODUCT_SERVICE, contextId = "brandClient")
public interface BrandClient extends BrandApi {

}
