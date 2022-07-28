package cn.alphahub.mall.search.api;

import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.search.domain.SkuModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 商品搜索 - Feign Client父类
 *
 * @author Weasley J
 * @date 2021年3月7日
 */
public interface SearchApi {

    /**
     * 保存上架商品至Elasticsearch中
     *
     * @param skuModels 商品SKU信息元数据集合
     * @return true|false
     */
    @PostMapping("/search/save/product")
    Result<Boolean> productStatusUp(@RequestBody List<SkuModel> skuModels);
}
