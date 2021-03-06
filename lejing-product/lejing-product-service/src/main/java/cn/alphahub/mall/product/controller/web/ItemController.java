package cn.alphahub.mall.product.controller.web;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.product.service.SkuInfoService;
import cn.alphahub.mall.product.vo.SkuItemVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <b>商品sku详情页Controller</b>
 *
 * @author Weasley J
 * @version 1.0
 * @date 2021/03/09
 */
@Controller
public class ItemController extends BaseController {

    @Resource
    SkuInfoService skuInfoService;

    /**
     * 根据skuId获取商品详情页
     *
     * @param skuId 商品skuId
     * @return 商品详情页html
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        //防止网关把404.html带过来
        SkuItemVO skuItemVo = skuInfoService.getSkuItemBySkuId(skuId);
        if (ObjectUtils.isEmpty(skuItemVo) || Objects.equals(skuId, 404L)) {
            return "index";
        }
        model.addAttribute("item", skuItemVo);
        return "item";
    }

    /**
     * 根据skuId获取商品详情
     *
     * @param skuId 商品skuId
     * @return 商品详情页数据
     */
    @ResponseBody
    @GetMapping("/item/{skuId}")
    public BaseResult<SkuItemVO> skuItemJson(@PathVariable("skuId") Long skuId) {
        SkuItemVO skuItemVo = skuInfoService.getSkuItemBySkuId(skuId);
        return Objects.nonNull(skuItemVo) ? BaseResult.ok(skuItemVo) : BaseResult.fail();
    }
}
