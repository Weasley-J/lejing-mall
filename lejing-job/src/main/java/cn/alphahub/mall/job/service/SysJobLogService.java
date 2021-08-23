package cn.alphahub.mall.job.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 定时任务调度日志表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
public interface SysJobLogService extends IService<SysJobLog> {

    /**
     * 查询定时任务调度日志表分页列表
     *
     * @param pageDomain 分页数据
     * @param sysJobLog  分页对象
     * @return 定时任务调度日志表分页数据
     */
    PageResult<SysJobLog> queryPage(PageDomain pageDomain, SysJobLog sysJobLog);

}
