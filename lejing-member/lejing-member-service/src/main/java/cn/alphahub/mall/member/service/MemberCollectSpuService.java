package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberCollectSpu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员收藏的商品Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberCollectSpuService extends IService<MemberCollectSpu> {

    /**
     * 查询会员收藏的商品分页列表
     *
     * @param pageDomain       分页数据
     * @param memberCollectSpu 分页对象
     * @return 会员收藏的商品分页数据
     */
    PageResult<MemberCollectSpu> queryPage(PageDomain pageDomain, MemberCollectSpu memberCollectSpu);

}
