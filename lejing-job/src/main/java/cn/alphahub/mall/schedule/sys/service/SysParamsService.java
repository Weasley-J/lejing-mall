package cn.alphahub.mall.schedule.sys.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.sys.domain.SysParams;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 参数管理Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:21:20
 */
public interface SysParamsService extends IService<SysParams> {

    /**
     * 查询参数管理分页列表
     *
     * @param page      分页参数
     * @param sysParams 分页对象
     * @return 参数管理分页数据
     */
    PageResult<SysParams> queryPage(PageDomain page, SysParams sysParams);

}
