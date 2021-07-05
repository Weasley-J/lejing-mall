package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品评价Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface SpuCommentService extends IService<SpuComment> {

    /**
     * 查询商品评价分页列表
     *
     * @param pageDomain 分页数据
     * @param spuComment 分页对象
     * @return 商品评价分页数据
     */
    PageResult<SpuComment> queryPage(PageDomain pageDomain, SpuComment spuComment);

}
