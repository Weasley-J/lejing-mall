package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>商品秒杀-VO</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillSkuVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    /**
     * 当前商品秒杀的开始时间
     */
    private Long startTime;

    /**
     * 当前商品秒杀的结束时间
     */
    private Long endTime;

    /**
     * 当前商品秒杀的随机码
     */
    private String randomCode;
}
