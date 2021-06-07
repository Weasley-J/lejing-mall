package cn.alphahub.mall.ware.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 完成采购-值对象
 *
 * @author liuwenjign
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDoneVo implements Serializable {

    /**
     * 采购单id
     */
    @NotNull
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 采购项列表
     */
    private List<PurchaseItemVo> items;

}
