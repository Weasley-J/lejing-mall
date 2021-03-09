package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author liuwnjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPrice {

    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    private String name;

    private BigDecimal price;
}
