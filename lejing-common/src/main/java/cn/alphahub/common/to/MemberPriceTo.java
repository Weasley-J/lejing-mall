package cn.alphahub.common.to;

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
 * <p>
 * <b>会员价格 - 数据传输对象</b>
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPriceTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * sku名称
     */
    private String name;

    /**
     * sku价格
     */
    private BigDecimal price;
}
