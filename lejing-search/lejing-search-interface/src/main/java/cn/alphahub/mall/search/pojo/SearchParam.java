package cn.alphahub.mall.search.pojo;

import cn.alphahub.common.valid.group.QueryGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.ArrayList;
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
    private List<Long> brandId = new ArrayList<>();

    /**
     * 三级分类id
     */
    private Long catalog3Id;

    /**
     * 排序条件：sort=skuPrice/saleCount/hasStock_desc|asc
     */
    private String sort;

    /**
     * 是否显示有货：0（无货）, 1（有货），不传查全部
     */
    private Integer hasStock;

    /**
     * 价格区间查询: 1_6000 | _6000 | 6000_
     */
    private String skuPrice;

    /**
     * 按照属性进行筛选, 格式: attrId_attrValue, 如: attr = attrs=1_5寸:8寸&attrs=3_4核:8核&attrs=3_8G:12G
     */
    private List<String> attrs;

    /**
     * 当前页码
     */
    @Range(min = 1, max = Integer.MAX_VALUE, groups = {QueryGroup.class}, message = "当前页码必须>0")
    private Integer pageNum = 1;

    /**
     * 每页显示条数
     */
    private Integer pageSize = 16;

    /**
     * HttpServletRequest原生的所有查询条件
     * <p>
     * a <code>String</code> containing the query string or
     * <code>null</code> if the URL contains no query string. The value
     * is not decoded by the container.
     */
    private String queryString;
}
