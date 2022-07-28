package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 锁定库存的vo
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WareSkuLockVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单流水号
     */
    private String orderSn;

    /**
     * 需要锁住的所有库存信息
     **/
    private List<OrderItemVo> locks;
}
