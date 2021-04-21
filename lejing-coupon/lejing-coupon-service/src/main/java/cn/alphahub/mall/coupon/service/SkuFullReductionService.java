package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.SkuReductionTo;
import cn.alphahub.mall.coupon.domain.SkuFullReduction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品满减信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SkuFullReductionService extends IService<SkuFullReduction> {

    /**
     * 保存满减、优惠信息
     *
     * @param skuReductionTo
     */
    Boolean saveSkuReduction(SkuReductionTo skuReductionTo);

    /**
     * 查询商品满减信息分页列表
     *
     * @param pageDomain       分页数据
     * @param skuFullReduction 分页对象
     * @return 商品满减信息分页数据
     */
    PageResult<SkuFullReduction> queryPage(PageDomain pageDomain, SkuFullReduction skuFullReduction);

}
