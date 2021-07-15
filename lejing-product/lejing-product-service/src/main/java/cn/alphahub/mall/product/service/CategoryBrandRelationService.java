package cn.alphahub.mall.product.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.CategoryBrandRelation;
import cn.alphahub.mall.product.vo.BrandVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 品牌分类关联Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {
    /**
     * 查询品牌分类关联分页列表
     *
     * @param pageDomain            分页数据
     * @param categoryBrandRelation 分页对象
     * @return 品牌分类关联分页数据
     */
    PageResult<CategoryBrandRelation> queryPage(PageDomain pageDomain, CategoryBrandRelation categoryBrandRelation);

    /**
     * 新增品牌分类关联
     *
     * @param categoryBrandRelation 品牌分类关联元数据
     * @return 成功返回true, 失败返回false
     */
    boolean saveDetail(CategoryBrandRelation categoryBrandRelation);

    /**
     * 更新品牌信息
     *
     * @param brandId 品牌id
     * @param name    品牌名称
     * @return
     */
    boolean updateBrand(Long brandId, String name);

    /**
     * 级联更新-商品三级分类
     *
     * @param catId 商品三级分类id
     * @param name  分类名称
     * @return 成功返回true, 失败返回false
     */
    boolean updateCategory(Long catId, String name);

    /**
     * 根据分类id获取品牌列表
     *
     * @param catId 分类id
     * @return 商品id名称列表
     */
    List<BrandVO> getBrandsByCatId(Long catId);
}
