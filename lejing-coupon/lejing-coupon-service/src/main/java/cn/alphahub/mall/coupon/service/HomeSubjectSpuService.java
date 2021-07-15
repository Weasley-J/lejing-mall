package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubjectSpu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 专题商品Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface HomeSubjectSpuService extends IService<HomeSubjectSpu> {

    /**
     * 查询专题商品分页列表
     *
     * @param pageDomain     分页数据
     * @param homeSubjectSpu 分页对象
     * @return 专题商品分页数据
     */
    PageResult<HomeSubjectSpu> queryPage(PageDomain pageDomain, HomeSubjectSpu homeSubjectSpu);

}
