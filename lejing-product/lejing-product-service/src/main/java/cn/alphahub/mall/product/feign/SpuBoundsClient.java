package cn.alphahub.mall.product.feign;

import cn.alphahub.mall.coupon.api.SpuBoundsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 优惠券服务
 * 商品spu积分设置feign远程调用客户端
 *
 * @author liuwenjing
 */
@FeignClient(value = "lejing-coupon", contextId = "spuBoundsClient")
public interface SpuBoundsClient extends SpuBoundsApi {

}
