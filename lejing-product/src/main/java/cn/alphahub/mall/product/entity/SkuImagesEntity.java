package cn.alphahub.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * sku图片
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 04:31:16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("pms_sku_images")
public class SkuImagesEntity implements Serializable {
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
     * 图片地址
     */
    private String imgUrl;
    /**
     * 排序
     */
    private Integer imgSort;
    /**
     * 默认图[0 - 不是默认图，1 - 是默认图]
     */
    private Integer defaultImg;

}
