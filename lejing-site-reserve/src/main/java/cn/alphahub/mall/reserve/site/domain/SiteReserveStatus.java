package cn.alphahub.mall.reserve.site.domain;

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
 * 场地状态表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_reserve_status")
public class SiteReserveStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地状态id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long siteStatusId;

    /**
     * 场地场次id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteSessionId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 预定状态（0：可预定，1：已预定，2：不可预定, 默认0）
     */
    private Integer siteReserveStatus;

    /**
     * 场地库存总量
     */
    private Integer siteStockQuantity;

    /**
     * 场地可用库存
     */
    private Integer siteAvailableStock;

    /**
     * 生效日期（yyyy-MM-dd,关联eb_site_reserve_session表查询该日期对应的所有场次）
     */
    private Date effectDate;

    /**
     * 当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）
     */
    private BigDecimal currentPrice;

    /**
     * 场库存量
     */
    private Integer stockQuantity;

    /**
     * 冻结库存
     */
    private Integer stockFreeze;

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
