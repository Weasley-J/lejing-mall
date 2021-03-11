package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.service.AttrAttrgroupRelationService;
import cn.alphahub.mall.product.service.AttrGroupService;
import cn.alphahub.mall.product.service.AttrService;
import cn.alphahub.mall.product.service.CategoryService;
import cn.alphahub.mall.product.vo.AttrGroupRelationVO;
import cn.alphahub.mall.product.vo.AttrGroupVO;
import cn.alphahub.mall.product.vo.AttrGroupWithAttrsVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 属性分组Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-14 19:02:16
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController extends BaseController {
    @Resource
    AttrAttrgroupRelationService relationService;
    @Resource
    private AttrGroupService attrGroupService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private AttrService attrService;

    /**
     * 获取当属性分组id的关联关系
     *
     * @param attrGroupId 属性分组id
     * @return 商品属性列表
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public BaseResult<List<Attr>> getAttrRelations(@PathVariable("attrGroupId") Long attrGroupId) {
        return BaseResult.ok(attrService.getAttrRelations(attrGroupId));
    }

    /**
     * 获取分类下所有分组&关联属性
     *
     * @param catelogId 分类id
     * @return 分类下所有分组&关联属性列表
     */
    @GetMapping("/{catelogId}/withattr")
    public BaseResult<List<AttrGroupWithAttrsVO>> getAttrGroupWithAttr(@PathVariable("catelogId") Long catelogId) {
        //1、查出当前分类下的所有属性分组
        //2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVO> vos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return BaseResult.ok(vos);
    }

    /**
     * 获取当分组id的没有关联关系的属性
     *
     * @param attrGroupId 属性分组id
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param key         搜索关键字
     * @return 属性分组分页数据
     */
    @GetMapping("/{attrGroupId}/noattr/relation")
    public BaseResult<PageResult<Attr>> getAttrNoRelations(
            @PathVariable("attrGroupId") Long attrGroupId,
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows
    ) {
        PageDomain pageDomain = new PageDomain(page, rows);
        return BaseResult.ok(attrService.getAttrNoRelations(pageDomain, attrGroupId, key));
    }

    /**
     * 查询属性分组列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param attrGroup   属性分组元数据
     * @return 属性分组分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<AttrGroup>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            AttrGroup attrGroup
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<AttrGroup> pageResult = attrGroupService.queryPage(pageDomain, attrGroup);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 根据catelogId查询属性分组列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param catelogId   所属分类id
     * @param key         检索关键字
     * @return 属性分组分页数据
     */
    @GetMapping("/list/{catelogId}")
    public BaseResult<PageResult<AttrGroup>> listByCatelogId(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @PathVariable(value = "catelogId") Long catelogId,
            @RequestParam(value = "key", defaultValue = "") String key
    ) {
        PageResult<AttrGroup> pageResult = attrGroupService.queryPage(
                new PageDomain(page, rows, orderColumn, isAsc),
                AttrGroup.builder()
                        .catelogId(Objects.equals(catelogId, 0L) ? null : catelogId)
                        .build(),
                key
        );
        return BaseResult.ok(pageResult);
    }

    /**
     * 获取属性分组详情
     *
     * @param attrGroupId 属性分组主键id
     * @return 属性分组详细信息
     */
    @GetMapping("/info/{attrGroupId}")
    public BaseResult<AttrGroup> info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogPath = categoryService.getCategoryFullPath(catelogId);
        attrGroup.setCatelogPath(catelogPath);
        return ObjectUtils.anyNotNull(attrGroup) ? BaseResult.ok(attrGroup) : BaseResult.fail();
    }

    /**
     * 新增属性分组
     *
     * @param attrGroup 属性分组元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("save")
    public BaseResult<Boolean> save(@RequestBody AttrGroup attrGroup) {
        boolean save = attrGroupService.save(attrGroup);
        return toOperationResult(save);
    }

    /**
     * 修改属性分组
     *
     * @param attrGroup 属性分组,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("update")
    public BaseResult<Boolean> update(@RequestBody AttrGroup attrGroup) {
        boolean update = attrGroupService.updateById(attrGroup);
        return toOperationResult(update);
    }

    /**
     * 批量删除属性分组
     *
     * @param attrGroupIds 属性分组id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("delete/{attrGroupIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] attrGroupIds) {
        boolean delete = attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return toOperationResult(delete);
    }

    /**
     * 删除属性分组关联关系
     *
     * @param attrGroupVOList 属性分组集合
     */
    @PostMapping("attr/relation/delete")
    public BaseResult<Integer> deleteRelations(@RequestBody List<AttrGroupVO> attrGroupVOList) {
        Integer rows = attrService.removeRelations(attrGroupVOList);
        return BaseResult.ok(rows);
    }

    /**
     * 新增属性分组关联关系
     *
     * @param relationVos 属性分组集合
     */
    @PostMapping("/attr/relation")
    public BaseResult<Boolean> addBatchRelations(@RequestBody List<AttrGroupRelationVO> relationVos) {
        return BaseResult.ok(relationService.addBatchRelations(relationVos));
    }
}
