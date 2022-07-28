package cn.alphahub.mall.product.controller.web;

import cn.alphahub.mall.common.core.domain.Result;
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
 * 商品sku详情页Controller
 *
 * @author Weasley J
 * @version 1.0
 * @date 2021/03/09
 */
@Controller
public class ItemController {

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
    public Result<SkuItemVO> skuItemJson(@PathVariable("skuId") Long skuId) {
        SkuItemVO skuItemVo = skuInfoService.getSkuItemBySkuId(skuId);
        return Objects.nonNull(skuItemVo) ? Result.ok(skuItemVo) : Result.fail();
    }
}
