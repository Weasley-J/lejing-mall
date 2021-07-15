package cn.alphahub.mall.app.pojo.vo;

import cn.alphahub.common.enums.CouponStatus;
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
 * 场地预约订单列表-VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SiteOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前登录用户id
     */
    private String userId;

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
     * 场次生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;

    /**
     * 场次开始时间点（规则: 24H制，时分中间用:号隔开；如: 08:00, 09:30，可精确到具体分钟）
     */
    private String sessionStartTime;

    /**
     * 场次结束时间点（规则: 24H制，时分中间用:号隔开；如: 20:00, 21:30，可精确到具体分钟）
     */
    private String sessionFinishTime;

    /**
     * 场次集合各个场次价格相加之和
     */
    private Integer totalPrice;

    /**
     * 状态（1-未使用；2-已使用；3-已过期；4-已关闭；5-已退款；默认未使用：1）
     */
    private Integer couponStatus;

    /**
     * 入场券状态名称
     */
    private String couponStatusName;

    public String getCouponStatusName() {
        return CouponStatus.statusName(this.couponStatus);
    }
}
