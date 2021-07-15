package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.Coupon;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface CouponService extends IService<Coupon> {

    /**
     * 查询优惠券信息分页列表
     *
     * @param pageDomain 分页数据
     * @param coupon     分页对象
     * @return 优惠券信息分页数据
     */
    PageResult<Coupon> queryPage(PageDomain pageDomain, Coupon coupon);

}
