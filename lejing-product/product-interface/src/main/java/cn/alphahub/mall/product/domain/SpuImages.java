package cn.alphahub.mall.product.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * spu图片
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_spu_images")
public class SpuImages implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * spu_id
	 */
    private Long spuId;

	/**
	 * 图片名
	 */
    private String imgName;

	/**
	 * 图片地址
	 */
    private String imgUrl;

	/**
	 * 顺序
	 */
    private Integer imgSort;

	/**
	 * 是否默认图
	 */
    private Integer defaultImg;

}