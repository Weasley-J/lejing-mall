package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberCollectSpuMapper;
import cn.alphahub.mall.member.domain.MemberCollectSpu;
import cn.alphahub.mall.member.service.MemberCollectSpuService;

import java.util.List;

/**
 * 会员收藏的商品Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@Service("memberCollectSpuService")
public class MemberCollectSpuServiceImpl extends ServiceImpl<MemberCollectSpuMapper, MemberCollectSpu> implements MemberCollectSpuService {

    /**
     * 查询会员收藏的商品分页列表
     *
     * @param pageDomain   分页数据
     * @param memberCollectSpu 分页对象
     * @return 会员收藏的商品分页数据
     */
    @Override
    public PageResult<MemberCollectSpu> queryPage(PageDomain pageDomain, MemberCollectSpu memberCollectSpu) {
        pageDomain.startPage();
        QueryWrapper<MemberCollectSpu> wrapper = new QueryWrapper<>(memberCollectSpu);
        List<MemberCollectSpu> list = this.list(wrapper);
        PageInfo<MemberCollectSpu> pageInfo = new PageInfo<>(list);
        PageResult<MemberCollectSpu> pageResult = PageResult.<MemberCollectSpu>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}