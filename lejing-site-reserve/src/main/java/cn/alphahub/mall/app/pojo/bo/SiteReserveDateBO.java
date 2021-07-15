package cn.alphahub.mall.app.pojo.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 场地预约时间-BO
 *
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteReserveDateBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 生效日期（yyyy-MM-dd,关联eb_site_reserve_session表查询该日期对应的所有场次）
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;

    /**
     * 开始营业时间（规则: 24H制，时分中间用":"号隔开；如: 08:00, 09:30，可精确到具体分钟）
     */
    private String siteOpenTime;

    /**
     * 结束营业时间（规则: 24H制，时分中间用":"号隔开；如: 19:00, 22:30，可精确到具体分钟）
     */
    private String siteCloseTime;

    /**
     * 当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）
     */
    private Integer currentPrice;

}
