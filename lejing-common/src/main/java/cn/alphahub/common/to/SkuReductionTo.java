package cn.alphahub.common.to;


import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * sku满减数据传输对象
 *
 * @author liuwenjjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuReductionTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = IdSerializer.class)
    private Long skuId;

    /**
     * 总数
     */
    private int fullCount;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 计数状态
     */
    private int countStatus;

    /**
     * 总价
     */
    private BigDecimal fullPrice;

    /**
     * 减少价格
     */
    private BigDecimal reducePrice;

    /**
     * 价格状态
     */
    private int priceStatus;

    /**
     * 会员价格列表
     */
    private List<MemberPriceTo> memberPrice;

}
