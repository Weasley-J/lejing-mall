package cn.alphahub.mall.order.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单操作历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oms_order_operate_history")
public class OrderOperateHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 订单id
	 */
    private Long orderId;

	/**
	 * 操作人[用户；系统；后台管理员]
	 */
    private String operateMan;

	/**
	 * 操作时间
	 */
    private Date createTime;

	/**
	 * 订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】
	 */
    private Integer orderStatus;

	/**
	 * 备注
	 */
    private String note;

}