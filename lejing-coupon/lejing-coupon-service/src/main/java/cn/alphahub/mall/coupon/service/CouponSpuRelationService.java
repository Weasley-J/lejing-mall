package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 优惠券与产品关联Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface CouponSpuRelationService extends IService<CouponSpuRelation> {

    /**
     * 查询优惠券与产品关联分页列表
     *
     * @param pageDomain        分页数据
     * @param couponSpuRelation 分页对象
     * @return 优惠券与产品关联分页数据
     */
    PageResult<CouponSpuRelation> queryPage(PageDomain pageDomain, CouponSpuRelation couponSpuRelation);

}
