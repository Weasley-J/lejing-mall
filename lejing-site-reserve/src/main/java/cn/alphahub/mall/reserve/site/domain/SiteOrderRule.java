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
import java.util.Date;

/**
 * 场地预约订单规则表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_order_rule")
public class SiteOrderRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 使用规则
     */
    private String useRule;

    /**
     * 开场前可退款分钟数，<= 30min
     */
    private Integer orderRefundMinutes;

    /**
     * 订单过期退款规则：1-过期不退，2-过期自动退，3-过期申请退，默认1-过期不退
     */
    private String orderExpiredRefundRule;

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
