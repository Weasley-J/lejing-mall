package cn.alphahub.mall.order.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 邮费
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/04/25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FareVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 邮寄地址
     */
    private MemberAddressVo address;

    /**
     * 邮费
     */
    private BigDecimal fare;
}
