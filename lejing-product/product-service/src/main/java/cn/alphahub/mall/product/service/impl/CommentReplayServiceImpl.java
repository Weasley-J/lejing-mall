package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.CommentReplayMapper;
import cn.alphahub.mall.product.domain.CommentReplay;
import cn.alphahub.mall.product.service.CommentReplayService;

import java.util.List;

/**
 * 商品评价回复关系Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("commentReplayService")
public class CommentReplayServiceImpl extends ServiceImpl<CommentReplayMapper, CommentReplay> implements CommentReplayService {

    /**
     * 查询商品评价回复关系分页列表
     *
     * @param pageDomain   分页数据
     * @param commentReplay 分页对象
     * @return 商品评价回复关系分页数据
     */
    @Override
    public PageResult<CommentReplay> queryPage(PageDomain pageDomain, CommentReplay commentReplay) {
        pageDomain.startPage();
        QueryWrapper<CommentReplay> wrapper = new QueryWrapper<>(commentReplay);
        List<CommentReplay> list = this.list(wrapper);
        PageInfo<CommentReplay> pageInfo = new PageInfo<>(list);
        PageResult<CommentReplay> pageResult = PageResult.<CommentReplay>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}