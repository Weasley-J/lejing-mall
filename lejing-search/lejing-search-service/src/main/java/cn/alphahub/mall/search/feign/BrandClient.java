package cn.alphahub.mall.search.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <b>品牌信息-feign远程调用客户端</b>
 *
 * @author Weasley J
 * @version 1.0
 * @email 1432689025@qq.com
 * @date 2021/03/14
 */
@FeignClient(value = AppConstant.SEARCH_SERVICE, contextId = "brandClient")
public interface BrandClient extends BrandApi {

}
