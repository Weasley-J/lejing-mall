package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员价格
 *
 * @author liuwnjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPrice implements Serializable {

    /**
     * 会员id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;
}
