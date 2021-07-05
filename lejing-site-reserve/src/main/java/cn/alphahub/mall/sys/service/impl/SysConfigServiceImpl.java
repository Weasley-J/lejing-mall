package cn.alphahub.mall.sys.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysConfig;
import cn.alphahub.mall.sys.mapper.SysConfigMapper;
import cn.alphahub.mall.sys.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数配置表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:19:03
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    /**
     * 查询参数配置表分页列表
     *
     * @param pageDomain 分页数据
     * @param sysConfig  分页对象
     * @return 参数配置表分页数据
     */
    @Override
    public PageResult<SysConfig> queryPage(PageDomain pageDomain, SysConfig sysConfig) {
        pageDomain.startPage();
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>(sysConfig);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>参数配置表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SysConfig> getPageResult(QueryWrapper<SysConfig> wrapper) {
        List<SysConfig> list = this.list(wrapper);
        PageInfo<SysConfig> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SysConfig>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
