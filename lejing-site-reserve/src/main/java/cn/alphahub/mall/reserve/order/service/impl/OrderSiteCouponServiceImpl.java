package cn.alphahub.mall.reserve.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.order.domain.OrderSiteCoupon;
import cn.alphahub.mall.reserve.order.mapper.OrderSiteCouponMapper;
import cn.alphahub.mall.reserve.order.service.OrderSiteCouponService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地预约入场券卷号表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:17:37
 */
@Service("orderSiteCouponService")
public class OrderSiteCouponServiceImpl extends ServiceImpl<OrderSiteCouponMapper, OrderSiteCoupon> implements OrderSiteCouponService {

    /**
     * 查询场地预约入场券卷号表分页列表
     *
     * @param pageDomain      分页数据
     * @param orderSiteCoupon 分页对象
     * @return 场地预约入场券卷号表分页数据
     */
    @Override
    public PageResult<OrderSiteCoupon> queryPage(PageDomain pageDomain, OrderSiteCoupon orderSiteCoupon) {
        pageDomain.startPage();
        QueryWrapper<OrderSiteCoupon> wrapper = new QueryWrapper<>(orderSiteCoupon);

        // 这里可编写自己的业务查询wrapper，如果需要。
        // ...

        return getPageResult(wrapper);
    }

    /**
     * 根据查询构造器条件查询分页查询结果
     *
     * @param wrapper <b>场地预约入场券卷号表<b/>实体对象封装操作类
     * @return 实体对象分页查询结果
     */
    private PageResult<OrderSiteCoupon> getPageResult(QueryWrapper<OrderSiteCoupon> wrapper) {
        List<OrderSiteCoupon> list = this.list(wrapper);
        PageInfo<OrderSiteCoupon> pageInfo = new PageInfo<>(list);
        var pageResult = new PageResult<OrderSiteCoupon>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());
        return pageResult;
    }

}
