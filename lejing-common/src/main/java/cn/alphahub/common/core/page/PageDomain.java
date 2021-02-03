package cn.alphahub.common.core.page;

import cn.alphahub.common.util.SqlUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * Pagehelper分页数据实体
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
     * 每页显示数量,默认每页显示10条
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

    public String getOrderBy() {
        if (StringUtils.isBlank(orderColumn)) {
            return "";
        }
        return StringUtils.camelToUnderline(orderColumn) + " " + isAsc;
    }

    /**
     * 设置请求分页数据
     */
    public void startPage() {
        int pageNum = ObjectUtils.isEmpty(page) || this.getPage() <= 0 ? 1 : this.getPage();
        int pageSize = ObjectUtils.isEmpty(rows) || this.getRows() <= 0 ? 10 : this.getRows();
        if (ObjectUtils.isNotNull(pageNum, pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(this.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }
}
