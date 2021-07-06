package cn.alphahub.mall.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付宝交易状态枚举
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/07/06
 */
@Getter
@AllArgsConstructor
public enum TradeStatusEnum {

    /**
     * 交易完成（交易结束，不可退款）
     */
    TRADE_FINISHED("TRADE_FINISHED"),
    /**
     * 支付成功（交易支付成功）
     */
    TRADE_SUCCESS("TRADE_SUCCESS"),
    /**
     * 交易创建（等待买家付款）
     */
    WAIT_BUYER_PAY("WAIT_BUYER_PAY"),
    /**
     * 交易关闭（未付款交易超时关闭，或支付完成后全额退款）
     */
    TRADE_CLOSED("TRADE_CLOSED");

    /**
     * 状态名
     */
    private final String name;
}
