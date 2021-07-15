package cn.alphahub.mall.ware.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 合并采购单值对象
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergeVo {
    /**
     * 采购单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long purchaseId;

    /**
     * 合并类目id
     */
    private List<Long> items;
}
