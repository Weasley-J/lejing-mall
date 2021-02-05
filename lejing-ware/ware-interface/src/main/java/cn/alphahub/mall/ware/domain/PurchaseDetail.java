package cn.alphahub.mall.ware.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 仓储采购表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:37:25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wms_purchase_detail")
public class PurchaseDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
    @TableId
    private Long id;

	/**
	 * 采购单id
	 */
    private Long purchaseId;

	/**
	 * 采购商品id
	 */
    private Long skuId;

	/**
	 * 采购数量
	 */
    private Integer skuNum;

	/**
	 * 采购金额
	 */
    private BigDecimal skuPrice;

	/**
	 * 仓库id
	 */
    private Long wareId;

	/**
	 * 状态[0新建，1已分配，2正在采购，3已完成，4采购失败]
	 */
    private Integer status;

}
