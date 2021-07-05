package cn.alphahub.common.core.service;


import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

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
     * @param pageDomain 分页数据
     * @param domain     分页对象
     * @return 分页集合
     */
    PageResult<T> queryPage(PageDomain pageDomain, T domain);
}
