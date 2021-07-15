package cn.alphahub.mall.app.pojo.bo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 场地预约入场券卷号对象-BO
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteCouponBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id，订单号（eb_site_reserve_detail表主键）
     */
    private String orderMasterId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 券号，优惠券码，后台处理（规则：订单创建日期加上6位随机数-yyyyMMddxxxxxx，如：20210121687587）
     */
    private String couponCode;

    //private String couponCodeUrl;

    /**
     * 场地预约时间列表
     */
    private List<SiteReserveDateBO> siteReserveDates;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Boolean deleted;
}
