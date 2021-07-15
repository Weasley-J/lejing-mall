package cn.alphahub.mall.order.dto.vo;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * 订单确认页需要用的数据
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 会员收获地址列表
     **/
    List<MemberAddressVo> memberAddressVos;

    /**
     * 所有选中的购物项
     */
    List<OrderItemVo> items;

    // 发票记录...

    Map<Long, Boolean> stocks;

    /**
     * 订单总额
     */
    private BigDecimal total;
    /**
     * 商品数量
     */
    private Integer count;
    /**
     * 应付价格
     */
    private BigDecimal payPrice;
    /**
     * 优惠券（会员积分）
     */
    private Integer integration;
    /**
     * 防止重复提交的令牌
     */
    private String orderToken;

    /**
     * @return 订单总额
     */
    public BigDecimal getTotal() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (CollectionUtils.isNotEmpty(items)) {
            for (OrderItemVo item : items) {
                // 计算当前商品的总价格
                BigDecimal itemPrice = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                // 再计算全部商品的总价格
                totalPrice = totalPrice.add(itemPrice);
            }
        }
        return totalPrice.setScale(2, RoundingMode.DOWN);
    }

    /**
     * @return 商品数量
     */
    public Integer getCount() {
        Integer tempCount = 0;
        if (CollectionUtils.isNotEmpty(items)) {
            for (OrderItemVo item : items) {
                tempCount += item.getCount();
            }
        }
        return tempCount;
    }

    /**
     * 计算订单总额
     *
     * @return 付款价格
     */
    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
