package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.CouponMapper;
import cn.alphahub.mall.coupon.domain.Coupon;
import cn.alphahub.mall.coupon.service.CouponService;

import java.util.List;

/**
 * 优惠券信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Service("couponService")
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    /**
     * 查询优惠券信息分页列表
     *
     * @param pageDomain   分页数据
     * @param coupon 分页对象
     * @return 优惠券信息分页数据
     */
    @Override
    public PageResult<Coupon> queryPage(PageDomain pageDomain, Coupon coupon) {
        pageDomain.startPage();
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>(coupon);
        List<Coupon> list = this.list(wrapper);
        PageInfo<Coupon> pageInfo = new PageInfo<>(list);
        PageResult<Coupon> pageResult = PageResult.<Coupon>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}