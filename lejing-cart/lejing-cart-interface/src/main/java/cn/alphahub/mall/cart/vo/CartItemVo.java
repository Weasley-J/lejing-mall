package cn.alphahub.mall.cart.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 购物项内容
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 是否选中，默认true
     */
    private Boolean check = true;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片
     */
    private String image;

    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 计算当前购物项总价
     *
     * @return 当前购物项总价
     */
    public BigDecimal getTotalPrice() {
        if (Objects.nonNull(price) && Objects.nonNull(count)) {
            return price.multiply(BigDecimal.valueOf(count));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
