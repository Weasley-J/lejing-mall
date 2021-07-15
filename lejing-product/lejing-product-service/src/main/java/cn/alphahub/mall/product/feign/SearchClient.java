package cn.alphahub.mall.product.feign;


import cn.alphahub.mall.search.api.SearchApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * <b>搜索服务Feign客户端</b>
 * 保存上架商品到ES中
 *
 * @author liuwenjing
 */
@FeignClient(value = "lejing-search")
public interface SearchClient extends SearchApi {

}
