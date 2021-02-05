package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.CategoryBrandRelationMapper;
import cn.alphahub.mall.product.domain.CategoryBrandRelation;
import cn.alphahub.mall.product.service.CategoryBrandRelationService;

import java.util.List;

/**
 * 品牌分类关联Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements CategoryBrandRelationService {

    /**
     * 查询品牌分类关联分页列表
     *
     * @param pageDomain   分页数据
     * @param categoryBrandRelation 分页对象
     * @return 品牌分类关联分页数据
     */
    @Override
    public PageResult<CategoryBrandRelation> queryPage(PageDomain pageDomain, CategoryBrandRelation categoryBrandRelation) {
        pageDomain.startPage();
        QueryWrapper<CategoryBrandRelation> wrapper = new QueryWrapper<>(categoryBrandRelation);
        List<CategoryBrandRelation> list = this.list(wrapper);
        PageInfo<CategoryBrandRelation> pageInfo = new PageInfo<>(list);
        PageResult<CategoryBrandRelation> pageResult = PageResult.<CategoryBrandRelation>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}