package cn.alphahub.mall.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJob;
import cn.alphahub.mall.job.mapper.SysJobMapper;
import cn.alphahub.mall.job.service.SysJobService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时任务调度表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

    /**
     * 查询定时任务调度表分页列表
     *
     * @param pageDomain 分页数据
     * @param sysJob     分页对象
     * @return 定时任务调度表分页数据
     */
    @Override
    public PageResult<SysJob> queryPage(PageDomain pageDomain, SysJob sysJob) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SysJob> wrapper = new QueryWrapper<>(sysJob);
        // 2. 创建一个分页对象
        PageResult<SysJob> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SysJob> sysJobList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(sysJobList);
    }

}
