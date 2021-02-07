package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * spu信息介绍
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_spu_info_desc")
public class SpuInfoDesc implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 商品id
	 */
    @TableId
    private Long spuId;

	/**
	 * 商品介绍
	 */
    private String decript;

}
