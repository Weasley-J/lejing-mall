package cn.alphahub.mall.search.service;

import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.search.pojo.SearchParam;
import cn.alphahub.mall.search.pojo.SearchResult;

import java.util.List;

/**
 * <b>商品搜索服务顶层接口</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/07
 */
public interface SearchService {

    /**
     * 保存上架商品至Elasticsearch中
     *
     * @param skuModels 商品SKU信息数据
     * @return true|false
     */
    Boolean saveProduct(List<SkuModel> skuModels);

    /**
     * 删除商品
     *
     * @param skuId 商品spu id
     * @return true|false
     */
    Boolean deleteProductById(Long skuId);

    /**
     * 获取搜索结果列表
     *
     * @param param 搜索请求参数实体
     * @return 获取搜索结果
     */
    SearchResult search(SearchParam param);
}
