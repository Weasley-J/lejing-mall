package cn.alphahub.mall.reserve.app.pojo.vo;


import cn.alphahub.common.enums.CouponStatus;
import cn.alphahub.common.util.IdSerializer;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteDetailBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteReserveDateBO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 订单详情-VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteOrderDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地id（主键）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场地名称
     */
    private String siteTitle;

    /**
     * 入场券券号，优惠券码，后台处理（规则：订单创建日期加上6位随机数-yyyyMMddxxxxxx，如：20210121687587）
     */
    private String couponCode;

    /**
     * 入场券状态（1-未使用；2-已使用；3-已过期；4-已关闭；5-已退款；默认未使用：1）
     */
    private Integer couponStatus;

    /**
     * 入场券状态名称
     */
    private String couponStatusName;
    /**
     * 订单id，订单号（eb_site_reserve_detail表主键）
     */
    private String orderMasterId;
    /**
     * 用户手机号码
     */
    private String userPhone;
    /**
     * master order订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 实际支付金额（单位：分）,查询eb_order_master表order_act_amount字段
     */
    private Integer orderActAmount;
    /**
     * 场地预约时间列表
     */
    private List<SiteReserveDateBO> reserveDateList;
    /**
     * 场地其他信息(退款规则，使用须知，....)见eb_site_reserve_detail表
     */
    private List<SiteDetailBO> siteDetailList;

    public String getCouponStatusName() {
        return CouponStatus.statusName(this.couponStatus);
    }
}
