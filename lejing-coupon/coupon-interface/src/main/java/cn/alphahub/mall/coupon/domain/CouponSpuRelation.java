package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 优惠券与产品关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_coupon_spu_relation")
public class CouponSpuRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 优惠券id
	 */
    private Long couponId;

	/**
	 * spu_id
	 */
    private Long spuId;

	/**
	 * spu_name
	 */
    private String spuName;

}
