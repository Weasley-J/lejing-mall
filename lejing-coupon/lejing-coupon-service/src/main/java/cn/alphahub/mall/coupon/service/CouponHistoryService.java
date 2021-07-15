package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券领取历史记录Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface CouponHistoryService extends IService<CouponHistory> {

    /**
     * 查询优惠券领取历史记录分页列表
     *
     * @param pageDomain    分页数据
     * @param couponHistory 分页对象
     * @return 优惠券领取历史记录分页数据
     */
    PageResult<CouponHistory> queryPage(PageDomain pageDomain, CouponHistory couponHistory);

}
