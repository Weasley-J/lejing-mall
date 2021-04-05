package cn.alphahub.common.enumeration;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 电子入场券状态-枚举
 *
 * @author lwj
 */
public enum CouponStatus implements BaseStatus {
    /* 1-未使用  */
    UNUSED(1, "未使用"),
    /* 2-已使用 */
    USED(2, "已使用"),
    /* 3-已过期 */
    EXPIRED(2, "已过期"),
    /* 4-已关闭 */
    CLOSED(4, "已关闭"),
    /* 5-已退款 */
    REFUNDED(5, "已退款");

    @Getter
    @Setter
    private String statusName;

    @Getter
    @Setter
    private Integer statusCode;

    CouponStatus(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

    /**
     * 通过code取枚举
     *
     * @param statusCode status Code
     * @return 枚举
     */
    public static CouponStatus couponStatus(Integer statusCode) {
        if (ObjectUtils.isNotEmpty(statusCode)) {
            for (CouponStatus enums : CouponStatus.values()) {
                if (enums.getStatusCode().equals(statusCode)) {
                    return enums;
                }
            }
        }
        return null;
    }

    /**
     * 通过code取描述
     *
     * @param statusCode 状态码
     * @return 枚举描述
     */
    public static String statusName(Integer statusCode) {
        for (CouponStatus enums : CouponStatus.values()) {
            if (enums.getStatusCode().equals(statusCode)) {
                return enums.getStatusName();
            }
        }
        return "";
    }

    @Override
    public void print() {
        System.out.println("statusCode=" + statusCode + ", statusName=" + statusName);
    }

    @Override
    public String getName() {
        return this.statusName;
    }
}

interface BaseStatus {

    /**
     * 打印状态信息
     */
    void print();

    /**
     * 获取状态名称
     *
     * @return 状态名称
     */
    String getName();
}
