package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.HomeAdvMapper;
import cn.alphahub.mall.coupon.domain.HomeAdv;
import cn.alphahub.mall.coupon.service.HomeAdvService;

import java.util.List;

/**
 * 首页轮播广告Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("homeAdvService")
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvMapper, HomeAdv> implements HomeAdvService {

    /**
     * 查询首页轮播广告分页列表
     *
     * @param pageDomain   分页数据
     * @param homeAdv 分页对象
     * @return 首页轮播广告分页数据
     */
    @Override
    public PageResult<HomeAdv> queryPage(PageDomain pageDomain, HomeAdv homeAdv) {
        pageDomain.startPage();
        QueryWrapper<HomeAdv> wrapper = new QueryWrapper<>(homeAdv);
        List<HomeAdv> list = this.list(wrapper);
        PageInfo<HomeAdv> pageInfo = new PageInfo<>(list);
        PageResult<HomeAdv> pageResult = PageResult.<HomeAdv>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}