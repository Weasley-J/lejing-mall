package cn.alphahub.mall.coupon.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 优惠券分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_coupon_spu_category_relation")
public class CouponSpuCategoryRelation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@JsonSerialize(using = IdSerializer.class)
	private Long id;

	/**
	 * 优惠券id
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long couponId;

	/**
	 * 产品分类id
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long categoryId;

	/**
	 * 产品分类名称
	 */
    private String categoryName;

}
