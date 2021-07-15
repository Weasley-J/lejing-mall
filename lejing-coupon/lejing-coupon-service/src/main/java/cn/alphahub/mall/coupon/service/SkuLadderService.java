package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SkuLadder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品阶梯价格Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SkuLadderService extends IService<SkuLadder> {

    /**
     * 查询商品阶梯价格分页列表
     *
     * @param pageDomain 分页数据
     * @param skuLadder  分页对象
     * @return 商品阶梯价格分页数据
     */
    PageResult<SkuLadder> queryPage(PageDomain pageDomain, SkuLadder skuLadder);

}
