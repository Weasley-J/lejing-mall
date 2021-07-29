package cn.alphahub.mall.product.vo;

import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>商品详情页-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuItemVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sku基本信息的获取-pms_sku_info
     */
    private SkuInfo info;

    /**
     * 是否有库存
     */
    private Boolean hasStock;

    /**
     * 秒杀商品的优惠信息
     */
    private SeckillSkuRelation seckillSkuRelation;

    /**
     * 获取spu的介绍
     */
    private SpuInfoDesc desc;

    /**
     * sku的图片信息-pms_sku_images
     */
    private List<SkuImages> images;

    /**
     * 获取spu的销售属性组合列表
     */
    private List<SkuItemSaleAttrVO> saleAttr;

    /**
     * 获取spu的规格参数信息列表
     */
    private List<SpuItemAttrGroupVO> groupAttrs;

}
