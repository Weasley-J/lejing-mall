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
 * 场地预约场次对象-VO
 *
 * @author liuwenjing
 * @date 2021-02-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSessionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场次id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteSessionId;

    /**
     * 场地id
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
     * 当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）
     */
    private Integer currentPrice;

    /**
     * 该场次状态（0：可预定，1：已预定，2：不可预定）
     */
    private Integer sessionStatus;

    /**
     * 该场次状态名称
     */
    private String statusName;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Boolean deleted;

    /**
     * 设置该场场次的状态名称
     *
     * @param sessionStatus 该场次状态码
     */
    public void setStatusName(Integer sessionStatus) {
        switch (sessionStatus) {
            case 0:
                this.statusName = "可预定";
                break;
            case 1:
                this.statusName = "已预定";
                break;
            case 2:
                this.statusName = "不可预定";
                break;
            default:
                break;
        }
    }
}
