package cn.alphahub.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 首页轮播广告
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:22:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_home_adv")
public class HomeAdvEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 名字
	 */
	private String name;
	/**
	 * 图片地址
	 */
	private String pic;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 点击数
	 */
	private Integer clickCount;
	/**
	 * 广告详情连接地址
	 */
	private String url;
	/**
	 * 备注
	 */
	private String note;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 发布者
	 */
	private Long publisherId;
	/**
	 * 审核者
	 */
	private Long authId;

}
