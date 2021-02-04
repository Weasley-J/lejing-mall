package cn.alphahub.mall.product.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.product.service.SpuInfoService;

import java.util.Arrays;

/**
 * spu信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:20:39
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController extends BaseController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 查询spu信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param spuInfo spu信息,字段选择性传入,默认等值查询
     * @return spu信息分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:spuinfo:list")
    public BaseResult<PageResult<SpuInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuInfo spuInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuInfo> pageResult = spuInfoService.queryPage(pageDomain, spuInfo);
        return (BaseResult<PageResult<SpuInfo>>) toPageableResult(pageResult);
    }

    /**
     * 获取spu信息详情
     *
     * @param id spu信息主键id
     * @return spu信息详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<SpuInfo> info(@PathVariable("id") Long id){
        SpuInfo spuInfo = spuInfoService.getById(id);
        return (BaseResult<SpuInfo>) toResponseResult(spuInfo);
    }

    /**
     * 新增spu信息
     *
     * @param spuInfo spu信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:spuinfo:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ SpuInfo spuInfo) {
        boolean save = spuInfoService.save(spuInfo);
        return toOperationResult(save);
    }

    /**
     * 修改spu信息
     *
     * @param spuInfo spu信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ SpuInfo spuInfo) {
        boolean update = spuInfoService.updateById(spuInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除spu信息
     *
     * @param ids spu信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("product:spuinfo:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = spuInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}