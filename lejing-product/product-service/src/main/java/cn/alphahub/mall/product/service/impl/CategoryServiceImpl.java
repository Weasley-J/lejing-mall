package cn.alphahub.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.mapper.CategoryMapper;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.service.CategoryService;

import java.util.List;

/**
 * 商品三级分类Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 查询商品三级分类分页列表
     *
     * @param pageDomain   分页数据
     * @param category 分页对象
     * @return 商品三级分类分页数据
     */
    @Override
    public PageResult<Category> queryPage(PageDomain pageDomain, Category category) {
        pageDomain.startPage();
        QueryWrapper<Category> wrapper = new QueryWrapper<>(category);
        List<Category> list = this.list(wrapper);
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        PageResult<Category> pageResult = PageResult.<Category>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}