package cn.alphahub.mall.coupon.domain;

import cn.alphahub.common.to.SkuInfoTo;
import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 秒杀活动商品关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_seckill_sku_relation")
public class SeckillSkuRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 活动id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long promotionId;

    /**
     * 活动场次id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long promotionSessionId;

    /**
     * 商品id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long skuId;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀总量
     */
    private BigDecimal seckillCount;

    /**
     * 每人限购数量
     */
    private BigDecimal seckillLimit;

    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * 当前商品秒杀的开始时间
     */
    @TableField(exist = false)
    private Long startTime;

    /**
     * 当前商品秒杀的结束时间
     */
    @TableField(exist = false)
    private Long endTime;

    /**
     * 当前商品秒杀的随机码
     */
    @TableField(exist = false)
    private String randomCode;

    /**
     * 秒杀商品数据
     */
    @TableField(exist = false)
    private SkuInfoTo skuInfo;
}
