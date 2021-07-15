package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.MemberPrice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品会员价格Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface MemberPriceService extends IService<MemberPrice> {

    /**
     * 查询商品会员价格分页列表
     *
     * @param pageDomain  分页数据
     * @param memberPrice 分页对象
     * @return 商品会员价格分页数据
     */
    PageResult<MemberPrice> queryPage(PageDomain pageDomain, MemberPrice memberPrice);

}
