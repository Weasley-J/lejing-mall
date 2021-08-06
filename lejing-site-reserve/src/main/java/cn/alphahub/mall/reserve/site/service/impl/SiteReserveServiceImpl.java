package cn.alphahub.mall.reserve.site.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.site.domain.SiteReserve;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveMapper;
import cn.alphahub.mall.reserve.site.service.SiteReserveService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地预约主表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Service("siteReserveService")
public class SiteReserveServiceImpl extends ServiceImpl<SiteReserveMapper, SiteReserve> implements SiteReserveService {

    /**
     * 查询场地预约主表分页列表
     *
     * @param pageDomain  分页数据
     * @param siteReserve 分页对象
     * @return 场地预约主表分页数据
     */
    @Override
    public PageResult<SiteReserve> queryPage(PageDomain pageDomain, SiteReserve siteReserve) {
        pageDomain.startPage();
        QueryWrapper<SiteReserve> wrapper = new QueryWrapper<>(siteReserve);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地预约主表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SiteReserve> getPageResult(QueryWrapper<SiteReserve> wrapper) {
        List<SiteReserve> list = this.list(wrapper);
        PageInfo<SiteReserve> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SiteReserve>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
