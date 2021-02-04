package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.service.ProductAttrValueService;

import java.util.Arrays;

/**
 * spu属性值Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@RestController
@RequestMapping("product/productattrvalue")
public class ProductAttrValueController extends BaseController {
    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 查询spu属性值列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param productAttrValue spu属性值,字段选择性传入,默认等值查询
     * @return spu属性值分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:productattrvalue:list")
    public BaseResult<PageResult<ProductAttrValue>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            ProductAttrValue productAttrValue
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<ProductAttrValue> pageResult = productAttrValueService.queryPage(pageDomain, productAttrValue);
        return (BaseResult<PageResult<ProductAttrValue>>) toPageableResult(pageResult);
    }

    /**
     * 获取spu属性值详情
     *
     * @param id spu属性值主键id
     * @return spu属性值详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<ProductAttrValue> info(@PathVariable("id") Long id){
        ProductAttrValue productAttrValue = productAttrValueService.getById(id);
        return (BaseResult<ProductAttrValue>) toResponseResult(productAttrValue);
    }

    /**
     * 新增spu属性值
     *
     * @param productAttrValue spu属性值元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:productattrvalue:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ ProductAttrValue productAttrValue) {
        boolean save = productAttrValueService.save(productAttrValue);
        return toOperationResult(save);
    }

    /**
     * 修改spu属性值
     *
     * @param productAttrValue spu属性值,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ ProductAttrValue productAttrValue) {
        boolean update = productAttrValueService.updateById(productAttrValue);
        return toOperationResult(update);
    }

    /**
     * 批量删除spu属性值
     *
     * @param ids spu属性值id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:productattrvalue:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = productAttrValueService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}