package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.service.AttrService;

import java.util.Arrays;

/**
 * 商品属性Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@RestController
@RequestMapping("product/attr")
public class AttrController extends BaseController {
    @Autowired
    private AttrService attrService;

    /**
     * 查询商品属性列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param attr 商品属性,字段选择性传入,默认等值查询
     * @return 商品属性分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public BaseResult<PageResult<Attr>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Attr attr
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Attr> pageResult = attrService.queryPage(pageDomain, attr);
        return (BaseResult<PageResult<Attr>>) toPageableResult(pageResult);
    }

    /**
     * 获取商品属性详情
     *
     * @param attrId 商品属性主键id
     * @return 商品属性详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<Attr> info(@PathVariable("attrId") Long attrId){
        Attr attr = attrService.getById(attrId);
        return (BaseResult<Attr>) toResponseResult(attr);
    }

    /**
     * 新增商品属性
     *
     * @param attr 商品属性元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ Attr attr) {
        boolean save = attrService.save(attr);
        return toOperationResult(save);
    }

    /**
     * 修改商品属性
     *
     * @param attr 商品属性,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ Attr attr) {
        boolean update = attrService.updateById(attr);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品属性
     *
     * @param attrIds 商品属性id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:attr:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] attrIds){
        boolean delete = attrService.removeByIds(Arrays.asList(attrIds));
        return toOperationResult(delete);
    }
}