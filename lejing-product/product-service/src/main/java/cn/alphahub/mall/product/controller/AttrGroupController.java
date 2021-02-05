package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.AttrGroup;
import cn.alphahub.mall.product.service.AttrGroupService;

import java.util.Arrays;

/**
 * 属性分组Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:31
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController extends BaseController {
    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 查询属性分组列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param attrGroup 属性分组,字段选择性传入,默认等值查询
     * @return 属性分组分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:attrgroup:list")
    public BaseResult<PageResult<AttrGroup>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            AttrGroup attrGroup
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<AttrGroup> pageResult = attrGroupService.queryPage(pageDomain, attrGroup);
        return (BaseResult<PageResult<AttrGroup>>) toPageableResult(pageResult);
    }

    /**
     * 获取属性分组详情
     *
     * @param attrGroupId 属性分组主键id
     * @return 属性分组详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("product:attrgroup:info")
    public BaseResult<AttrGroup> info(@PathVariable("attrGroupId") Long attrGroupId){
        AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
        return (BaseResult<AttrGroup>) toResponseResult(attrGroup);
    }

    /**
     * 新增属性分组
     *
     * @param attrGroup 属性分组元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ AttrGroup attrGroup) {
        boolean save = attrGroupService.save(attrGroup);
        return toOperationResult(save);
    }

    /**
     * 修改属性分组
     *
     * @param attrGroup 属性分组,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ AttrGroup attrGroup) {
        boolean update = attrGroupService.updateById(attrGroup);
        return toOperationResult(update);
    }

    /**
     * 批量删除属性分组
     *
     * @param attrGroupIds 属性分组id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:attrgroup:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] attrGroupIds){
        boolean delete = attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        return toOperationResult(delete);
    }
}
