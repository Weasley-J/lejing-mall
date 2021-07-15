package cn.alphahub.mall.app.pojo.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单快照详情对象-VO
 *
 * @author liuwenjing
 * @date 2021-02-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteOrderSnapVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单id，订单号
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long orderId;

    /**
     * 主订单id（关联eb_order_master主键order_master_id，）
     */
    private String orderMasterId;

    /**
     * 快照信息关联的产品id（全局唯一的产品，id如：场地预约的场地id等）
     */
    private String itemId;

    /**
     * item名称
     */
    private String itemTitle;

    /**
     * item副标题
     */
    private String itemSubtitle;

    /**
     * item品牌
     */
    private String itemBrand;

    /**
     * itemurl链接（图片超链接）
     */
    private String itemUrl;

    /**
     * item邮资，邮费
     */
    private Integer itemPostagePrice;

    /**
     * item数量
     */
    private Integer itemQuantity;

    /**
     * item数量单位
     */
    private String itemQuantityUnit;

    /**
     * 折扣金额(单位：分)
     */
    private Integer itemDiscountPrice;

    /**
     * 订单单价(单位：分)
     */
    private Integer itemUnitPrice;
}
