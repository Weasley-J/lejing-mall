package cn.alphahub.mall.ware.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 库存工作单
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wms_ware_order_task_detail")
public class WareOrderTaskDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * sku_id
	 */
    private Long skuId;

	/**
	 * sku_name
	 */
    private String skuName;

	/**
	 * 购买个数
	 */
    private Integer skuNum;

	/**
	 * 工作单id
	 */
    private Long taskId;

	/**
	 * 仓库id
	 */
    private Long wareId;

	/**
	 * 1-已锁定 2-已解锁 3-已扣减
	 */
    private Integer lockStatus;

}
