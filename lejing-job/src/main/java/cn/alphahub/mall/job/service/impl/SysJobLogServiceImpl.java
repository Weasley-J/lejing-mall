package cn.alphahub.mall.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJobLog;
import cn.alphahub.mall.job.mapper.SysJobLogMapper;
import cn.alphahub.mall.job.service.SysJobLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度日志表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
@Service
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

    /**
     * 查询定时任务调度日志表分页列表
     *
     * @param pageDomain 分页数据
     * @param sysJobLog  分页对象
     * @return 定时任务调度日志表分页数据
     */
    @Override
    public PageResult<SysJobLog> queryPage(PageDomain pageDomain, SysJobLog sysJobLog) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SysJobLog> wrapper = new QueryWrapper<>(sysJobLog);
        // 2. 创建一个分页对象
        PageResult<SysJobLog> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SysJobLog> sysJobLogList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(sysJobLogList);
    }

}
