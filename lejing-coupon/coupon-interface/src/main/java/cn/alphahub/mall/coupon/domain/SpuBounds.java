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
 * 商品spu积分设置
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_spu_bounds")
public class SpuBounds implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 *
	 */
    private Long spuId;

	/**
	 * 成长积分
	 */
    private BigDecimal growBounds;

	/**
	 * 购物积分
	 */
    private BigDecimal buyBounds;

	/**
	 * 优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]
	 */
    private Integer work;

}
