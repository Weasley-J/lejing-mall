package cn.alphahub.mall.search.feign;

import cn.alphahub.mall.product.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <b>品牌信息-feign客户端</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/14
 */
@FeignClient(value = "lejing-product", contextId = "brandClient")
public interface BrandClient extends BrandApi {

}
