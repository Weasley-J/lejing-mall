package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 付款数据
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户订单号 必填
     */
    private String outTradeNo;
    /**
     * 订单名称 必填
     */
    private String subject;
    /**
     * 付款金额 必填
     */
    private String totalAmount;
    /**
     * 商品描述 可空
     */
    private String body;
}
