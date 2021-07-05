package cn.alphahub.mall.search.feign;

import cn.alphahub.common.constant.AppConstant;
import cn.alphahub.mall.product.api.AttrApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 商品属性-feign远程调用客户端
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@FeignClient(name = AppConstant.SEARCH_SERVICE, contextId = "attrClient")
public interface AttrClient extends AttrApi {

}
