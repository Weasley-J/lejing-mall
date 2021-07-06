package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 付款异步回调数据
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/07/06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PayAsyncVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date gmt_create;
    private String charset;
    private Date gmt_payment;
    private Date notify_time;
    private String subject;
    private String sign;
    /**
     * 支付者的id
     */
    private String buyer_id;
    /**
     * 订单的信息
     */
    private String body;
    private BigDecimal invoice_amount;
    private String version;
    /**
     * 通知id
     */
    private String notify_id;

    private String fund_bill_list;
    /**
     * 通知类型； trade_status_sync
     */
    private String notify_type;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 支付的总额
     */
    private BigDecimal total_amount;
    /**
     * 交易状态  TRADE_SUCCESS
     */
    private String trade_status;
    /**
     * 流水号
     */
    private String trade_no;
    private String auth_app_id;
    /**
     * 商家收到的款
     */
    private BigDecimal receipt_amount;
    private BigDecimal point_amount;
    /**
     * 应用id
     */
    private String app_id;
    /**
     * 最终支付的金额
     */
    private BigDecimal buyer_pay_amount;
    /**
     * 签名类型
     */
    private String sign_type;
    /**
     * 商家的id
     */
    private String seller_id;
}
