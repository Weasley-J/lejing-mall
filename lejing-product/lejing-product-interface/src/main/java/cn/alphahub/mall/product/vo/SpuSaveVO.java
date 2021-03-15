package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 保存Spu-VO
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpuSaveVO implements Serializable {

    /**
     * spu名称
     */
    private String spuName;

    /**
     * spu描述
     */
    private String spuDescription;

    /**
     * spu三级分类id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long catalogId;

    /**
     * spu品牌id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long brandId;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 发布状态
     */
    private Integer publishStatus;

    /**
     * 详情描述
     */
    private List<String> decript;

    /**
     * 图片列表
     */
    private List<String> images;

    /**
     * 优惠信息
     */
    private Bounds bounds;

    /**
     * 基础属性列表
     */
    private List<BaseAttrs> baseAttrs;

    /**
     * 商品sku列表
     */
    private List<Skus> skus;
}
