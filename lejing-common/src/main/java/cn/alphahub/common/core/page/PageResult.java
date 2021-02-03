package cn.alphahub.common.core.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页查询结果集对象
 *
 * @param <T> 分页实体对象
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 总条数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long totalPage;

    /**
     * 当前页数据
     */
    private List<T> items;
}
