package cn.alphahub.mall.product.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * spu信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_spu_info")
public class SpuInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 商品名称
     */
    private String spuName;

    /**
     * 商品描述
     */
    private String spuDescription;

    /**
     * 所属分类id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long catalogId;

    /**
     * 品牌id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long brandId;

    /**
     *
     */
    private BigDecimal weight;

    /**
     * 上架状态[0 - 下架，1 - 上架]
     */
    private Integer publishStatus;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

}
