package cn.alphahub.mall.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单详情数据 - 查询参数
 *
 * @author liuwening
 * @version 1.0
 * @date 2021/08/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class OrderDetailReq implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单号
     */
    private String orderSn;
}
