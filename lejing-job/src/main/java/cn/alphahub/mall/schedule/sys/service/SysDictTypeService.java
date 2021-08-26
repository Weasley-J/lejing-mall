package cn.alphahub.mall.schedule.sys.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.sys.domain.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 字典类型Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:21:20
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 查询字典类型分页列表
     *
     * @param page        分页参数
     * @param sysDictType 分页对象
     * @return 字典类型分页数据
     */
    PageResult<SysDictType> queryPage(PageDomain page, SysDictType sysDictType);

}
