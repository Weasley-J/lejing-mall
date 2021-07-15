package cn.alphahub.mall.product.feign;

import cn.alphahub.mall.ware.api.WareSkuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 库存服务
 * 查询商品库存-feign远程调用客户端
 *
 * @author liuwenjing
 */
@FeignClient(value = "lejing-ware", contextId = "wareSkuClient")
public interface WareSkuClient extends WareSkuApi {

}
