package cn.alphahub.mall.schedule.job.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.job.domain.QuartzJobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * quartz定时任务调度日志Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
public interface QuartzJobLogService extends IService<QuartzJobLog> {

    /**
     * 查询quartz定时任务调度日志分页列表
     *
     * @param page         分页参数
     * @param quartzJobLog 分页对象
     * @return quartz定时任务调度日志分页数据
     */
    PageResult<QuartzJobLog> queryPage(PageDomain page, QuartzJobLog quartzJobLog);

}
