package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeAdv;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 首页轮播广告Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface HomeAdvService extends IService<HomeAdv> {

    /**
     * 查询首页轮播广告分页列表
     *
     * @param pageDomain 分页数据
     * @param homeAdv    分页对象
     * @return 首页轮播广告分页数据
     */
    PageResult<HomeAdv> queryPage(PageDomain pageDomain, HomeAdv homeAdv);

}
