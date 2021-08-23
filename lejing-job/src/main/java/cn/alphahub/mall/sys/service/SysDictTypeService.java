package cn.alphahub.mall.sys.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典类型Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:08:07
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 查询字典类型分页列表
     *
     * @param pageDomain  分页数据
     * @param sysDictType 分页对象
     * @return 字典类型分页数据
     */
    PageResult<SysDictType> queryPage(PageDomain pageDomain, SysDictType sysDictType);

}
