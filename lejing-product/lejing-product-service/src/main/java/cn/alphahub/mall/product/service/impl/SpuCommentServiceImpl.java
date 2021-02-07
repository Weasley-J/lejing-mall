package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.SpuCommentMapper;
import cn.alphahub.mall.product.domain.SpuComment;
import cn.alphahub.mall.product.service.SpuCommentService;

import java.util.List;

/**
 * 商品评价Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@Service("spuCommentService")
public class SpuCommentServiceImpl extends ServiceImpl<SpuCommentMapper, SpuComment> implements SpuCommentService {

    /**
     * 查询商品评价分页列表
     *
     * @param pageDomain   分页数据
     * @param spuComment 分页对象
     * @return 商品评价分页数据
     */
    @Override
    public PageResult<SpuComment> queryPage(PageDomain pageDomain, SpuComment spuComment) {
        pageDomain.startPage();
        QueryWrapper<SpuComment> wrapper = new QueryWrapper<>(spuComment);
        List<SpuComment> list = this.list(wrapper);
        PageInfo<SpuComment> pageInfo = new PageInfo<>(list);
        PageResult<SpuComment> pageResult = PageResult.<SpuComment>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}