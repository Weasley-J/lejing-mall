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
 * 退货原因
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oms_order_return_reason")
public class OrderReturnReason implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * 退货原因名
	 */
    private String name;

	/**
	 * 排序
	 */
    private Integer sort;

	/**
	 * 启用状态
	 */
    private Integer status;

	/**
	 * create_time
	 */
    private Date createTime;

}
