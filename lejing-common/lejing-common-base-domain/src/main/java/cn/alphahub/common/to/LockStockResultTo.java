package cn.alphahub.common.to;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 库存锁定结果
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LockStockResultTo implements Serializable {
    /**
     * 所有SKU锁定库存成功
     */
    private Boolean isAllSkuLocked;
    /**
     * 锁定库存元数据集
     */
    private List<SkuLockStock> skuLockStocks;

    /**
     * 锁定库存元数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class SkuLockStock implements Serializable {
        /**
         * SKU ID
         */
        @JsonSerialize(using = ToStringSerializer.class)
        private Long skuId;
        /**
         * 锁定数量
         */
        private Integer num;
        /**
         * 是否锁定成功
         */
        private Boolean locked;
    }
}
