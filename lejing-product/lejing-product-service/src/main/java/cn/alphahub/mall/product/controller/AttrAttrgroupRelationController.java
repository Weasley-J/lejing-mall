package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import cn.alphahub.mall.product.service.AttrAttrgroupRelationService;

import java.util.Arrays;

/**
 * 属性&属性分组关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:46:24
 */
@RestController
@RequestMapping("product/attrattrgrouprelation")
public class AttrAttrgroupRelationController extends BaseController {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 查询属性&属性分组关联列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param attrAttrgroupRelation 属性&属性分组关联,字段选择性传入,默认为等值查询
     * @return 属性&属性分组关联分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:attrattrgrouprelation:list")
    public BaseResult<PageResult<AttrAttrgroupRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            AttrAttrgroupRelation attrAttrgroupRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<AttrAttrgroupRelation> pageResult = attrAttrgroupRelationService.queryPage(pageDomain, attrAttrgroupRelation);
        return (BaseResult<PageResult<AttrAttrgroupRelation>>) toPageableResult(pageResult);
    }

    /**
     * 获取属性&属性分组关联详情
     *
     * @param id 属性&属性分组关联主键id
     * @return 属性&属性分组关联详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:attrattrgrouprelation:info")
    public BaseResult<AttrAttrgroupRelation> info(@PathVariable("id") Long id){
        AttrAttrgroupRelation attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);
        return (BaseResult<AttrAttrgroupRelation>) toResponseResult(attrAttrgroupRelation);
    }

    /**
     * 新增属性&属性分组关联
     *
     * @param attrAttrgroupRelation 属性&属性分组关联元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:attrattrgrouprelation:save")
    public BaseResult<Boolean> save(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
        boolean save = attrAttrgroupRelationService.save(attrAttrgroupRelation);
        return toOperationResult(save);
    }

    /**
     * 修改属性&属性分组关联
     *
     * @param attrAttrgroupRelation 属性&属性分组关联,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("product:attrattrgrouprelation:update")
    public BaseResult<Boolean> update(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
        boolean update = attrAttrgroupRelationService.updateById(attrAttrgroupRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除属性&属性分组关联
     *
     * @param ids 属性&属性分组关联id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:attrattrgrouprelation:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
