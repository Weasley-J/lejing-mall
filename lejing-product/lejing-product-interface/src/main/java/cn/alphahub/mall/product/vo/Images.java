package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品图片
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Images implements Serializable {
    /**
     * 图片URL
     */
    private String imgUrl;
    /**
     * 默认图片
     */
    private Integer defaultImg;
}
