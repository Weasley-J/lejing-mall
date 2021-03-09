package cn.alphahub.mall.product.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Category;
import cn.alphahub.mall.product.mapper.CategoryMapper;
import cn.alphahub.mall.product.service.CategoryBrandRelationService;
import cn.alphahub.mall.product.service.CategoryService;
import cn.alphahub.mall.product.vo.SecondCategoryVO;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 商品三级分类Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@Slf4j
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 查询商品三级分类分页列表
     *
     * @param pageDomain 分页数据
     * @param category   分页对象
     * @return 商品三级分类分页数据
     */
    @Override
    public PageResult<Category> queryPage(PageDomain pageDomain, Category category) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<Category> wrapper = new QueryWrapper<>(category);
        // 2. 创建一个分页对象
        PageResult<Category> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<Category> categoryList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(categoryList);
    }

    @Override
    public List<Category> getFirstLevelCategories() {
        log.info("查出所有1级分类...");
        long start = System.currentTimeMillis();
        List<Category> categories = this.baseMapper.selectList(
                new QueryWrapper<Category>().lambda().eq(Category::getParentCid, 0));
        log.info("消耗时间：{} 毫秒", (System.currentTimeMillis() - start));
        return categories;
    }

    @Override
    public Map<String, List<SecondCategoryVO>> getCatalogJson() {
        long start = System.currentTimeMillis();
        List<Category> firstLevelCategories = getFirstLevelCategories();
        //封装数据
        Map<String, List<SecondCategoryVO>> map = firstLevelCategories.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 遍历1级分类,查找1级分类子级-2级分类菜单
            QueryWrapper<Category> wrapper = new QueryWrapper<>();
            List<Category> categories = baseMapper.selectList(wrapper.lambda().eq(Category::getParentCid, v.getCatId()));
            List<SecondCategoryVO> secondCategoryVos = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(categories)) {
                // 遍历2级分类, 查找2级分类子级-3级分类
                secondCategoryVos = categories.stream().map(secondCategory -> {
                    // 查找3级分类
                    QueryWrapper<Category> wrapper3 = new QueryWrapper<>();
                    List<Category> thirdCategoryList = baseMapper.selectList(wrapper3.lambda().eq(Category::getParentCid, secondCategory.getCatId()));
                    List<SecondCategoryVO.ThirdCategoryVO> thirdCategoryVos = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(thirdCategoryList)) {
                        // 封装3级分类
                        thirdCategoryVos = thirdCategoryList.stream().map(thirdCategory -> SecondCategoryVO.ThirdCategoryVO.builder()
                                .catalog2Id(secondCategory.getCatId())
                                .id(thirdCategory.getCatId())
                                .name(thirdCategory.getName())
                                .build()).collect(Collectors.toList());
                    }
                    return SecondCategoryVO.builder()
                            .catalog1Id(v.getCatId())
                            .catalog3List(thirdCategoryVos)
                            .id(secondCategory.getCatId())
                            .name(secondCategory.getName())
                            .build();
                }).collect(Collectors.toList());
            }
            return secondCategoryVos;
        }));
        log.info("查出三级分类耗时: {} 毫秒", DateUtil.spendMs(start));
        return map;
    }

    /**
     * 查出所有分类及其子分类， 树形结构组装
     *
     * @return 分类及其子分类树形数据
     */
    @Override
    public List<Category> listWithTree() {
        // 1. 查出所有分类
        List<Category> categories = categoryMapper.selectList(null);
        // 一级类目
        // 2. 组装父子结构的数据
        return categories.stream()
                .filter(category -> category.getParentCid() == 0)
                .peek(menu -> menu.setChildren(getChildrenList(menu, categories)))
                .sorted(Comparator.comparingInt(Category::getSort)).collect(Collectors.toList());
    }

    @Override
    public boolean removeMenusByIds(List<Long> ids) {
        //TODO 1. 检查当前删除的菜单是否被别的地方引用
        int batchIds = categoryMapper.deleteBatchIds(ids);
        return batchIds >= 1;
    }

    /**
     * 找到catelogId所属分类的完整路径
     *
     * @param catelogId 所属分类id
     * @return [父, 子, 孙], [2,25,166]
     */
    @Override
    public Long[] getCatelogFullPath(Long catelogId) {
        List<Long> list = new ArrayList<>();
        List<Long> parentPath = getParentPath(catelogId, list);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

    /**
     * 级联更新-商品三级分类
     *
     * @param category 商品三级分类,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @Override
    public boolean updateCasecade(Category category) {
        boolean b1 = this.updateById(category);
        boolean b2 = this.categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
        return b1 && b2;
    }

    /**
     * 递归方法找父路径
     *
     * @param catelogId 分类id
     * @return
     */
    private List<Long> getParentPath(Long catelogId, List<Long> initialList) {
        // 收集当前节点的id
        initialList.add(catelogId);
        Category category = this.getById(catelogId);
        if (!Objects.equals(category.getParentCid(), 0L)) {
            getParentPath(category.getParentCid(), initialList);
        }
        //找到的是逆序的,使用Collections转换一下: [225,25,2] -> [2,25,225]
        Collections.reverse(initialList);
        return initialList;
    }

    /**
     * 递归查找所有菜单的子菜单
     *
     * @param rootCategory  当前菜单
     * @param allCategories 所有菜单
     * @return 菜单的子菜单
     */
    private List<Category> getChildrenList(@Nonnull Category rootCategory, @Nonnull List<Category> allCategories) {
        return allCategories.stream()
                .filter(category -> category.getParentCid().equals(rootCategory.getCatId()))
                //找子菜单
                .peek(category -> category.setChildren(getChildrenList(category, allCategories)))
                //菜单排序,sort越小越靠前
                .sorted(Comparator.comparingInt(value -> ObjectUtils.isNull(value.getSort()) ? 0 : value.getSort()))
                .collect(Collectors.toList());
    }
}
