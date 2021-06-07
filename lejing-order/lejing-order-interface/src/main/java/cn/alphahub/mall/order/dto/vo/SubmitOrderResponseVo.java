package cn.alphahub.mall.order.dto.vo;

import cn.alphahub.mall.order.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 提交订单响应数据
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitOrderResponseVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单数据
     */
    private Order order;

    /**
     * 错误状态码
     **/
    private Integer code;

    /**
     * 库存锁定结果
     */
    private String msg;
}
