package cn.alphahub.mall.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJob;
import cn.alphahub.mall.job.mapper.SysJobMapper;
import cn.alphahub.mall.job.service.SysJobService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:15:04
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    /**
     * 查询定时任务调度表分页列表
     *
     * @param page   分页参数
     * @param sysJob 分页对象
     * @return 定时任务调度表分页数据
     */
    @Override
    public PageResult<SysJob> queryPage(PageDomain page, SysJob sysJob) {
        PageResult<SysJob> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<SysJob> sysJobList = this.list(Wrappers.lambdaQuery(sysJob));
        return pageResult.getPage(sysJobList);
    }

}
