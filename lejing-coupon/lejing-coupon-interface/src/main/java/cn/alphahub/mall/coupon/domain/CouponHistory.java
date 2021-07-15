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
import java.util.Date;

/**
 * 优惠券领取历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_coupon_history")
public class CouponHistory implements Serializable {
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
	 * 会员id
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long memberId;

	/**
	 * 会员名字
	 */
    private String memberNickName;

	/**
	 * 获取方式[0->后台赠送；1->主动领取]
	 */
    private Integer getType;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 使用状态[0->未使用；1->已使用；2->已过期]
	 */
    private Integer useType;

	/**
	 * 使用时间
	 */
    private Date useTime;

	/**
	 * 订单id
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long orderId;

	/**
	 * 订单号
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long orderSn;

}
