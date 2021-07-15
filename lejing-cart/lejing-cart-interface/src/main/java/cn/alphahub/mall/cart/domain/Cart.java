package cn.alphahub.mall.cart.domain;

import cn.alphahub.mall.cart.vo.CartItemVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车存放的商品信息
 * <p>需要计算的属性需要重写get方法，保证每次获取属性都会进行计算</p>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购物车子项信息
     */
    List<CartItemVo> items;

    /**
     * 商品数量
     */
    private Integer countNum;

    /**
     * 商品类型数量
     */
    private Integer countType;

    /**
     * 商品总价
     */
    private BigDecimal totalAmount;

    /**
     * 减免价格
     */
    private BigDecimal reduce = new BigDecimal("0.00");

    /**
     * <p>设置购物车元数据</p>
     * <p>商品数量、商品类型数量、商品总价</p>
     *
     * @param cart 原来
     * @return 整个购物车存放的商品信息
     */
    public Cart buildCartMetaData(Cart cart) {
        if (!CollectionUtils.isEmpty(cart.getItems())) {
            cart.setCountNum(getCountNum());
            cart.setCountType(getCountType());
            cart.setTotalAmount(getTotalAmount());
        }
        return cart;
    }

    public Integer getCountNum() {
        int count = 0;
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo item : items) {
                count += item.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count = 0;
        if (!CollectionUtils.isEmpty(items)) {
            count = items.size();
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        // 计算购物项总价
        if (!CollectionUtils.isEmpty(items)) {
            for (CartItemVo cartItem : items) {
                if (cartItem.getCheck()) {
                    amount = amount.add(cartItem.getTotalPrice());
                }
            }
        }
        // 计算优惠后的价格
        return amount.subtract(getReduce());
    }
}
