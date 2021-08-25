package cn.alphahub.common.core.page;

import cn.alphahub.common.util.SqlUtil;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Set;

import static com.baomidou.mybatisplus.core.toolkit.StringUtils.camelToUnderline;

/**
 * Pagehelper分页对象
 *
 * @author liuwenjing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 页码,当前记录起始索引,默认第1页
     */
    private Integer page;

    /**
     * 每页显示条数,默认每页显示10行
     */
    private Integer rows;

    /**
     * 排序排序字段
     */
    private String orderColumn;

    /**
     * 排序方式desc或者asc,默认asc
     * desc-倒序排序(从大到小排序)
     * asc-正序排序(从小到大排序)
     */
    private String isAsc = "asc";

    /**
     * 初始化一个页码和当前页显示条数的构造器
     *
     * @param page 页码
     * @param rows 当前页显示行数
     */
    public PageDomain(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
    }

    /**
     * 设置请求分页数据
     */
    public void startPage() {
        int pageNum = ObjectUtils.isEmpty(page) || this.getPage() <= 0 ? 1 : this.getPage();
        int pageSize = ObjectUtils.isEmpty(rows) || this.getRows() <= 0 ? 10 : this.getRows();
        if (ObjectUtils.allNotNull(pageNum, pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    public String getOrderBy() {
        if (StringUtils.isBlank(orderColumn)) {
            return "";
        }
        Set<String> set = Set.of("asc", "desc");
        return set.contains(isAsc.toLowerCase()) ? camelToUnderline(orderColumn) + " " + isAsc : "";
    }
}
