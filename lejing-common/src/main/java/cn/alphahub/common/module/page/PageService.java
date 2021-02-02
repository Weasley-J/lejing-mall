package cn.alphahub.common.module.page;

import cn.alphahub.common.pojo.PageResult;

/**
 * 分页顶层接口
 *
 * @author liuwenjing
 * @date 2021-01-25
 */
public interface PageService<T> {
    /**
     * 查询分页列表
     *
     * @param page   当前页码, 默认第一页
     * @param rows   本页条数，默认每页显示10条
     * @param domain 分页对象
     * @return 分页结合
     */
    PageResult<T> selectPage(Integer page, Integer rows, T domain);
}
