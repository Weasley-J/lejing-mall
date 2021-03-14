package cn.alphahub.mall.search.pojo;

import cn.alphahub.mall.search.domain.SkuModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>搜索结果响应数据实体</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品信息列表
     */
    private List<SkuModel> product;

    /**
     * 当前页码
     */
    private Integer pageNum;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页码
     */
    private Integer totalPages;

    /**
     * 面包屑页码数据列表
     */
    private List<Integer> pageNavs;

    /**
     * 当前查询到的结果，所有涉及到的品牌
     */
    private List<BrandVO> brands;

    /**
     * 当前查询到的结果，所有涉及到的所有属性
     */
    private List<AttrVO> attrs;

    /**
     * 当前查询到的结果，所有涉及到的所有分类
     */
    private List<CatalogVO> catalogs;

    /**
     * 面包屑导航数据
     */
    private List<NavVO> navs;

    /**
     * 属性id面包屑导航数据
     */
    private List<Long> attrIds = new ArrayList<>();

    /**
     * 搜索页面面包屑导航
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NavVO {
        /**
         * 导航名称
         */
        private String navName;
        /**
         * 导航值
         */
        private String navValue;
        /**
         * 导航链接
         */
        private String link;
    }

    /**
     * 商品品牌
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BrandVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 品牌id
         */
        private Long brandId;
        /**
         * 品牌名称
         */
        private String brandName;
        /**
         * 品牌图标
         */
        private String brandImg;
    }

    /**
     * 商品属性
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttrVO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 属性id
         */
        private Long attrId;
        /**
         * 属性名称
         */
        private String attrName;
        /**
         * 属性值集合
         */
        private List<String> attrValue;
    }

    /**
     * 商品分类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CatalogVO implements Serializable {
        private static final long serialVersionUID = 1L;
        /**
         * 分类id
         */
        private Long catalogId;
        /**
         * 分类名称
         */
        private String catalogName;
    }
}
