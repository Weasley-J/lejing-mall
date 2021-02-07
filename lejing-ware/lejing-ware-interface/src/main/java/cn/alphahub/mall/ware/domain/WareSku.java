package cn.alphahub.mall.ware.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品库存
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("wms_ware_sku")
public class WareSku implements Serializable {
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
	 * 仓库id
	 */
    private Long wareId;

	/**
	 * 库存数
	 */
    private Integer stock;

	/**
	 * sku_name
	 */
    private String skuName;

	/**
	 * 锁定库存
	 */
    private Integer stockLocked;

}
