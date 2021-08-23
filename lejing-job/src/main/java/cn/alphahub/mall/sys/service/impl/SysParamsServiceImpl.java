package cn.alphahub.mall.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysParams;
import cn.alphahub.mall.sys.mapper.SysParamsMapper;
import cn.alphahub.mall.sys.service.SysParamsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数管理Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:08:07
 */
@Service
public class SysParamsServiceImpl extends ServiceImpl<SysParamsMapper, SysParams> implements SysParamsService {

    /**
     * 查询参数管理分页列表
     *
     * @param pageDomain 分页数据
     * @param sysParams  分页对象
     * @return 参数管理分页数据
     */
    @Override
    public PageResult<SysParams> queryPage(PageDomain pageDomain, SysParams sysParams) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SysParams> wrapper = new QueryWrapper<>(sysParams);
        // 2. 创建一个分页对象
        PageResult<SysParams> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SysParams> sysParamsList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(sysParamsList);
    }

}
