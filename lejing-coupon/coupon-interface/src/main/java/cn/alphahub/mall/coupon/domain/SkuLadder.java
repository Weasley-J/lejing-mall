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
 * 商品阶梯价格
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_sku_ladder")
public class SkuLadder implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * spu_id
	 */
    private Long skuId;

	/**
	 * 满几件
	 */
    private Integer fullCount;

	/**
	 * 打几折
	 */
    private BigDecimal discount;

	/**
	 * 折后价
	 */
    private BigDecimal price;

	/**
	 * 是否叠加其他优惠[0-不可叠加，1-可叠加]
	 */
    private Integer addOther;

}