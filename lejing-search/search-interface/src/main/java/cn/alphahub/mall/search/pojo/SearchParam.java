package cn.alphahub.mall.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <b>搜索请求参数实体</b>
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/03/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 全文匹配关键字
     */
    private String keyword;

    /**
     * 品牌id,可以多选
     */
    private List<Long> brandId;

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件：sort=price/salecount/hotscore_desc/asc
     */
    private String sort;

    /**
     * 是否显示有货
     */
    private Integer hasStock;

    /**
     * 价格区间查询
     */
    private String skuPrice;

    /**
     * 按照属进行筛选
     */
    private List<String> attrs;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 原生的所有查询条件
     */
    private String _queryString;
}
