package cn.alphahub.mall.reserve.site.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.site.domain.SiteReserveSession;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveSessionMapper;
import cn.alphahub.mall.reserve.site.service.SiteReserveSessionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地预约场次表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Service("siteReserveSessionService")
public class SiteReserveSessionServiceImpl extends ServiceImpl<SiteReserveSessionMapper, SiteReserveSession> implements SiteReserveSessionService {

    /**
     * 查询场地预约场次表分页列表
     *
     * @param pageDomain         分页数据
     * @param siteReserveSession 分页对象
     * @return 场地预约场次表分页数据
     */
    @Override
    public PageResult<SiteReserveSession> queryPage(PageDomain pageDomain, SiteReserveSession siteReserveSession) {
        pageDomain.startPage();
        QueryWrapper<SiteReserveSession> wrapper = new QueryWrapper<>(siteReserveSession);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地预约场次表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SiteReserveSession> getPageResult(QueryWrapper<SiteReserveSession> wrapper) {
        List<SiteReserveSession> list = this.list(wrapper);
        PageInfo<SiteReserveSession> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SiteReserveSession>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
