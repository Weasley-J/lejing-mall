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
 * 场地预约主表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_reserve")
public class SiteReserve implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地id（主键）
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场地编码
     */
    private String siteCode;

    /**
     * 项目编码（同一类场地项编码相同）
     */
    private String projectCode;

    /**
     * 订单类型（1：预约，2：出租，默认：1）
     */
    private String siteOrderType;

    /**
     * 场地名称
     */
    private String siteTitle;

    /**
     * 场地名称的副标题
     */
    private String siteSubtitle;

    /**
     * 预约价格（单位：分
     */
    private BigDecimal sitePrice;

    /**
     * 场地的位置描述信息
     */
    private String siteLocationDesc;

    /**
     * 场地所属社区
     */
    private String siteCommunity;

    /**
     * 场地管理员电话
     */
    private String siteManagerPhone;

    /**
     * 开始营业时间（规则: 24H制，时分中间用":"号隔开；如: 08:00, 09:30，可精确到具体分钟）
     */
    private String siteOpenTime;

    /**
     * 结束营业时间（规则: 24H制，时分中间用":"号隔开；如: 19:00, 22:30，可精确到具体分钟）
     */
    private String siteCloseTime;

    /**
     * 场地图片（多个图片以","隔开，如：http://baidu.com/521.jpg,http://baidu.com/546.jpg）
     */
    private String imageUrl;

    /**
     * 上架状态（0-待上架，1-已上架，2-已下架）
     */
    private Integer siteShelfStatus;

    /**
     * 营运状态（0-不可用，不营运；1-可用，可运营；默认：1）
     */
    private Integer siteOpenStatus;

    /**
     * 排序字段，数值越大，越靠前
     */
    private Integer sort;

    /**
     * 删除状态（0：未删，1：已删，默认：未删除）
     */
    private Integer deleted;

    /**
     * 历史预约量
     */
    private Long siteReserveCount;

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
