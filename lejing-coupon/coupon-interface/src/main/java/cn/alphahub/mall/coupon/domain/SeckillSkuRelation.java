package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @date 2021-02-06 02:47:18
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
    private Long id;

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
    private BigDecimal seckillCount;

	/**
	 * 每人限购数量
	 */
    private BigDecimal seckillLimit;

	/**
	 * 排序
	 */
    private Integer seckillSort;

}
