package cn.alphahub.mall.reserve.site.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.site.domain.SiteInvalidSession;
import cn.alphahub.mall.reserve.site.mapper.SiteInvalidSessionMapper;
import cn.alphahub.mall.reserve.site.service.SiteInvalidSessionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地预约不可用场次表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Service("siteInvalidSessionService")
public class SiteInvalidSessionServiceImpl extends ServiceImpl<SiteInvalidSessionMapper, SiteInvalidSession> implements SiteInvalidSessionService {

    /**
     * 查询场地预约不可用场次表分页列表
     *
     * @param pageDomain         分页数据
     * @param siteInvalidSession 分页对象
     * @return 场地预约不可用场次表分页数据
     */
    @Override
    public PageResult<SiteInvalidSession> queryPage(PageDomain pageDomain, SiteInvalidSession siteInvalidSession) {
        pageDomain.startPage();
        QueryWrapper<SiteInvalidSession> wrapper = new QueryWrapper<>(siteInvalidSession);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地预约不可用场次表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SiteInvalidSession> getPageResult(QueryWrapper<SiteInvalidSession> wrapper) {
        List<SiteInvalidSession> list = this.list(wrapper);
        PageInfo<SiteInvalidSession> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SiteInvalidSession>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
