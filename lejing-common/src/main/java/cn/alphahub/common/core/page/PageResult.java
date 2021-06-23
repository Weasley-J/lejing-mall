package cn.alphahub.common.core.page;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页查询结果集对象
 *
 * @param <T> 分页对象
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 314159265354L;

    /**
     * 总条数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页数据
     */
    private List<T> items;

    /**
     * 开始设置请求分页数据
     */
    public void startPage(PageDomain pageDomain) {
        pageDomain.startPage();
    }

    /**
     * 获取泛型T的分页数据列表
     *
     * @param itemList Dao|Mapper执行select语句后的list<T>集合
     * @return 返回泛型T的分页数据
     */
    public PageResult<T> getPage(List<T> itemList) {
        PageInfo<T> pageInfo = new PageInfo<>(itemList);
        return PageResult.<T>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage(pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
    }
}
