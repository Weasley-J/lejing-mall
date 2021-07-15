package cn.alphahub.mall.site.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 场地预约场次表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_reserve_session")
public class SiteReserveSession implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场次id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long siteSessionId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

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
    private BigDecimal currentPrice;

    /**
     * 该场次状态（0：可预定，1：不可预定, 默认0）
     */
    private String sessionStatus;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注信息
     */
    private String remark;

}
