package cn.alphahub.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠信息
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bounds implements Serializable {

    private BigDecimal buyBounds;

    private BigDecimal growBounds;
}
