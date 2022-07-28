package cn.alphahub.mall.schedule.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.sys.domain.SysParams;
import cn.alphahub.mall.schedule.sys.mapper.SysParamsMapper;
import cn.alphahub.mall.schedule.sys.service.SysParamsService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数管理Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-28 22:03:32
 */
@Service
public class SysParamsServiceImpl extends ServiceImpl<SysParamsMapper, SysParams> implements SysParamsService {

    /**
     * 查询参数管理分页列表
     *
     * @param page      分页参数
     * @param sysParams 分页对象
     * @return 参数管理分页数据
     */
    @Override
    public PageResult<SysParams> queryPage(PageDomain page, SysParams sysParams) {
        PageResult<SysParams> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<SysParams> sysParamsList = this.list(Wrappers.lambdaQuery(sysParams));
        return pageResult.getPage(sysParamsList);
    }

}
