package cn.alphahub.mall.schedule.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.job.domain.QuartzJobLog;
import cn.alphahub.mall.schedule.job.mapper.QuartzJobLogMapper;
import cn.alphahub.mall.schedule.job.service.QuartzJobLogService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * quartz定时任务调度日志Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
@Service
public class QuartzJobLogServiceImpl extends ServiceImpl<QuartzJobLogMapper, QuartzJobLog> implements QuartzJobLogService {

    /**
     * 查询quartz定时任务调度日志分页列表
     *
     * @param page         分页参数
     * @param quartzJobLog 分页对象
     * @return quartz定时任务调度日志分页数据
     */
    @Override
    public PageResult<QuartzJobLog> queryPage(PageDomain page, QuartzJobLog quartzJobLog) {
        PageResult<QuartzJobLog> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<QuartzJobLog> quartzJobLogList = this.list(Wrappers.lambdaQuery(quartzJobLog));
        return pageResult.getPage(quartzJobLogList);
    }

}
