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
 * 秒杀活动
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_seckill_promotion")
public class SeckillPromotion implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@JsonSerialize(using = IdSerializer.class)
	private Long id;

	/**
	 * 活动标题
	 */
    private String title;

	/**
	 * 开始日期
	 */
    private Date startTime;

	/**
	 * 结束日期
	 */
    private Date endTime;

	/**
	 * 上下线状态
	 */
    private Integer status;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 创建人
	 */
	@JsonSerialize(using = IdSerializer.class)
	private Long userId;

}
