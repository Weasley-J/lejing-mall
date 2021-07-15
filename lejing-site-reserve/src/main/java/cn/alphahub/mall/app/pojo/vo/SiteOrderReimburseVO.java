package cn.alphahub.mall.app.pojo.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 场地预约订单退款值对象
 * eb_order_reimburse
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteOrderReimburseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long reimburseId;

    /**
     * 主订单id（关联eb_order_master主键order_master_id）
     */
    private String orderMasterId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商品名称
     */
    private String productTitle;

    /**
     * 退款金额（实际付款金额，单位：分）
     */
    private Integer actualPrice;

    /**
     * 收款账户（从哪付款就退到哪里）
     */
    private String receiveAccount;

    /**
     * 退款状态（0：等待退款，1：平台处理中，2：退款成功，3：退款失败，默认0等待退款）
     */
    private Integer reimburseStatus;

    /**
     * 申请退款日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reimburseApplyDate;

    /**
     * 平台处理日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date merchantProcessDate;

    /**
     * 退款成功日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date refundSuccessfulDate;
}
