package cn.alphahub.mall.order.excel.easypoi.dto.request;

import cn.alphahub.common.core.page.PageDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账单明细查询
 * <p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021年7月21日
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BillingDetailQueryRequest extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单号列表，可为多个
     */
    private List<String> orderNo;
    /**
     * 交易流水号
     */
    private String tradeSerialNumber;
    /**
     * 交易来源: 1 云商城
     */
    private Integer tradeSource;
    /**
     * 结算时间区间开始
     */
    private LocalDateTime settleTimeStart;
    /**
     * 结算时间区间结束
     */
    private LocalDateTime settleTimeEnd;
    /**
     * 支付方式：ALI_PAY, WECHAT_PAY
     */
    private String paymentType;
    /**
     * 支付时间区间开始
     */
    private LocalDateTime paymentTimeStart;
    /**
     * 支付时间区间结束
     */
    private LocalDateTime paymentTimeEnd;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 三方订单号
     */
    private String tripartiteOrderNumber;
    /**
     * sku id
     */
    private Long skuId;
    /**
     * 状态：0待付款，1已付款
     */
    private Integer status;
    /**
     * 结算模式：0 自营, 1 代理
     */
    private Integer settleMode;
    /**
     * 收支类型：1收入，-1支出
     * <ul>
     *     <li>支付类型：0 支付，1 退款 <-> 收支类型 0 支出，1 收入</li>
     * </ul>
     */
    private Integer incomeExpendType;
    /**
     * 非查询参数（根据根据查询条件不分页导出所有数据，默认：false）
     *
     * @ignore
     */
    @JsonIgnore
    private Boolean importAll = Boolean.FALSE;
}
