package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础属性
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseAttrs implements Serializable {
    /**
     * 属性id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long attrId;

    /**
     * 属性值
     */
    private String attrValues;

    /**
     * 显示描述信息
     */
    private Integer showDesc;
}
