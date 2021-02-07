package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀商品通知订阅
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_seckill_sku_notice")
public class SeckillSkuNotice implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * member_id
	 */
    private Long memberId;

	/**
	 * sku_id
	 */
    private Long skuId;

	/**
	 * 活动场次id
	 */
    private Long sessionId;

	/**
	 * 订阅时间
	 */
    private Date subcribeTime;

	/**
	 * 发送时间
	 */
    private Date sendTime;

	/**
	 * 通知方式[0-短信，1-邮件]
	 */
    private Integer noticeType;

}
