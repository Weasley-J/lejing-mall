package cn.alphahub.mall.site.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.site.domain.SiteReserveDetail;
import cn.alphahub.mall.site.domain.SiteReserveSession;
import cn.alphahub.mall.site.mapper.SiteReserveDetailMapper;
import cn.alphahub.mall.site.service.SiteReserveDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地详情表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Service("siteReserveDetailService")
public class SiteReserveDetailServiceImpl extends ServiceImpl<SiteReserveDetailMapper, SiteReserveDetail> implements SiteReserveDetailService {

    /**
     * 查询场地详情表分页列表
     *
     * @param pageDomain        分页数据
     * @param siteReserveDetail 分页对象
     * @return 场地详情表分页数据
     */
    @Override
    public PageResult<SiteReserveDetail> queryPage(PageDomain pageDomain, SiteReserveDetail siteReserveDetail) {
        pageDomain.startPage();
        QueryWrapper<SiteReserveDetail> wrapper = new QueryWrapper<>(siteReserveDetail);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地详情表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SiteReserveDetail> getPageResult(QueryWrapper<SiteReserveDetail> wrapper) {
        List<SiteReserveDetail> list = this.list(wrapper);
        PageInfo<SiteReserveDetail> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SiteReserveDetail>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
