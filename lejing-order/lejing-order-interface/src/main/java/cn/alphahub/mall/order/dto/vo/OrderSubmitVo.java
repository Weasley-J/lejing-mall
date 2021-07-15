package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单提交数据
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 收获地址的id
     **/
    private Long addrId;

    /**
     * 支付方式
     **/
    private Integer payType;
    //无需提交要购买的商品，去购物车再获取一遍
    //优惠、发票

    /**
     * 防重令牌
     **/
    private String orderToken;

    /**
     * 应付价格
     **/
    private BigDecimal payPrice;

    /**
     * 订单备注
     **/
    private String remarks;

    //用户相关的信息，直接去session中取用即可
}
