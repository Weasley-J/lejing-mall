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
 * 场地预约入场券卷号对象-VO
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteCouponVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id，订单号（eb_site_reserve_detail表主键）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long orderMasterId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场次id
     */
    private String siteSessionId;

    /**
     * 券号，优惠券码，后台处理（规则：订单创建日期加上6位随机数-yyyyMMddxxxxxx，如：20210121687587）
     */
    private String couponCode;

    /**
     * 状态（1-未使用；2-已使用；3-已过期；4-已关闭；5-已退款；默认未使用：1）
     */
    private Integer couponStatus;

    /**
     * 入场卷核销人员
     */
    private String writeOffUser;

    /**
     * 入场卷消费时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date consumeDate;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Boolean deleted;
}
