package cn.alphahub.mall.search.controller;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.exception.BusinessCodeEnum;
import cn.alphahub.common.valid.QueryGroup;
import cn.alphahub.mall.search.domain.SkuModel;
import cn.alphahub.mall.search.pojo.SearchParam;
import cn.alphahub.mall.search.pojo.SearchResult;
import cn.alphahub.mall.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品搜索首页Controller
 *
 * @author Weasley J
 * @date 2021年3月12日
 */
@Slf4j
@Controller
public class SearchController extends BaseController {

    @Resource
    private SearchService searchService;

    /**
     * 获取搜索结果html视图
     *
     * @param param 所有可能的搜索参数
     * @return 搜索列表html
     */
    @GetMapping("/list.html")
    public String list(SearchParam param, Model model) {
        // 根据搜索请求参数去ES中检索数据
        SearchResult result = searchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }

    /**
     * 保存上架商品至Elasticsearch中
     *
     * @param skuModels 商品SKU信息元数据集合
     * @return true|false
     */
    @ResponseBody
    @PostMapping("/search/save/product")
    public BaseResult<Boolean> productStatusUp(@RequestBody List<SkuModel> skuModels) {
        Boolean save = false;
        try {
            save = searchService.saveProduct(skuModels);
        } catch (Exception e) {
            log.error("上架商品保存至Elasticsearch中失败：{}\n", e.getClass(), e);
        }
        return save ? BaseResult.ok("保存成功") : BaseResult.fail(
                BusinessCodeEnum.PRODUCT_UP_EXCEPTION.getCode(),
                BusinessCodeEnum.PRODUCT_UP_EXCEPTION.getMessage());
    }

    /**
     * 获取收缩及如果列表
     *
     * @param param 搜索请求参数实体
     * @return 商品搜索结果数据
     */
    @ResponseBody
    @GetMapping("/search/list")
    public BaseResult<SearchResult> searchResult(@Validated(QueryGroup.class) SearchParam param) {
        return BaseResult.success(searchService.search(param));
    }
}
