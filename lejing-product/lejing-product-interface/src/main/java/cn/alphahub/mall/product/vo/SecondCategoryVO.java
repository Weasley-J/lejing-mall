package cn.alphahub.mall.product.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>二级分类</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecondCategoryVO implements Serializable {

    /**
     * 一级父分类的id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long catalog1Id;

    /**
     * 三级子分类
     */
    private List<ThirdCategoryVO> catalog3List;

    /**
     * 分类id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 三级分类vo
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThirdCategoryVO implements Serializable {

        /**
         * 父分类、二级分类id
         */
        @JsonSerialize(using = IdSerializer.class)
        private Long catalog2Id;

        /**
         * 分类id
         */
        @JsonSerialize(using = IdSerializer.class)
        private Long id;

        /**
         * 分类名称
         */
        private String name;
    }
}
