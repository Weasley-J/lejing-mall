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
 * 商品满减信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_sku_full_reduction")
public class SkuFullReduction implements Serializable {
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
	 * 满多少
	 */
    private BigDecimal fullPrice;

	/**
	 * 减多少
	 */
    private BigDecimal reducePrice;

	/**
	 * 是否参与其他优惠
	 */
    private Integer addOther;

}