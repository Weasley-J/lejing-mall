package cn.alphahub.mall.reserve.site.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.site.domain.SiteReserveStatus;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveStatusMapper;
import cn.alphahub.mall.reserve.site.service.SiteReserveStatusService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地状态表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Service("siteReserveStatusService")
public class SiteReserveStatusServiceImpl extends ServiceImpl<SiteReserveStatusMapper, SiteReserveStatus> implements SiteReserveStatusService {

    /**
     * 查询场地状态表分页列表
     *
     * @param pageDomain        分页数据
     * @param siteReserveStatus 分页对象
     * @return 场地状态表分页数据
     */
    @Override
    public PageResult<SiteReserveStatus> queryPage(PageDomain pageDomain, SiteReserveStatus siteReserveStatus) {
        pageDomain.startPage();
        QueryWrapper<SiteReserveStatus> wrapper = new QueryWrapper<>(siteReserveStatus);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地状态表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<SiteReserveStatus> getPageResult(QueryWrapper<SiteReserveStatus> wrapper) {
        List<SiteReserveStatus> list = this.list(wrapper);
        PageInfo<SiteReserveStatus> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<SiteReserveStatus>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
