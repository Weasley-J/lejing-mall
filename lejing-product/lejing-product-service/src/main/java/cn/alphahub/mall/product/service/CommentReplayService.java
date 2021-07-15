package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.CommentReplay;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品评价回复关系Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface CommentReplayService extends IService<CommentReplay> {

    /**
     * 查询商品评价回复关系分页列表
     *
     * @param pageDomain    分页数据
     * @param commentReplay 分页对象
     * @return 商品评价回复关系分页数据
     */
    PageResult<CommentReplay> queryPage(PageDomain pageDomain, CommentReplay commentReplay);

}
