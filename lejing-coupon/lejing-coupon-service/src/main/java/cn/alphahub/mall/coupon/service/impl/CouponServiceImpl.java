package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.Coupon;
import cn.alphahub.mall.coupon.mapper.CouponMapper;
import cn.alphahub.mall.coupon.service.CouponService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements CouponService {

    /**
     * 查询优惠券信息分页列表
     *
     * @param pageDomain 分页数据
     * @param coupon     分页对象
     * @return 优惠券信息分页数据
     */
    @Override
    public PageResult<Coupon> queryPage(PageDomain pageDomain, Coupon coupon) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>(coupon);
        // 2. 创建一个分页对象
        PageResult<Coupon> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<Coupon> couponList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(couponList);
    }

}
