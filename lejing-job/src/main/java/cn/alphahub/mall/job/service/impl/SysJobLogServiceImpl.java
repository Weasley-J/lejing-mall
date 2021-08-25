package cn.alphahub.mall.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJobLog;
import cn.alphahub.mall.job.mapper.SysJobLogMapper;
import cn.alphahub.mall.job.service.SysJobLogService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度日志表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:15:04
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

    /**
     * 查询定时任务调度日志表分页列表
     *
     * @param page      分页参数
     * @param sysJobLog 分页对象
     * @return 定时任务调度日志表分页数据
     */
    @Override
    public PageResult<SysJobLog> queryPage(PageDomain page, SysJobLog sysJobLog) {
        PageResult<SysJobLog> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<SysJobLog> sysJobLogList = this.list(Wrappers.lambdaQuery(sysJobLog));
        return pageResult.getPage(sysJobLogList);
    }

}
