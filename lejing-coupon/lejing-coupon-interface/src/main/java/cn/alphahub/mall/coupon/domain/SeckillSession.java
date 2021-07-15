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
 * 秒杀活动场次
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_seckill_session")
public class SeckillSession implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@JsonSerialize(using = IdSerializer.class)
	private Long id;

	/**
	 * 场次名称
	 */
    private String name;

	/**
	 * 每日开始时间
	 */
    private Date startTime;

	/**
	 * 每日结束时间
	 */
    private Date endTime;

	/**
	 * 启用状态
	 */
    private Integer status;

	/**
	 * 创建时间
	 */
    private Date createTime;

}
