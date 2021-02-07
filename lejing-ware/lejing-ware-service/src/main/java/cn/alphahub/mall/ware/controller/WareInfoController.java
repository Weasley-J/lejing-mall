package cn.alphahub.mall.ware.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.domain.WareInfo;
import cn.alphahub.mall.ware.service.WareInfoService;

import java.util.Arrays;

/**
 * 仓库信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController extends BaseController {
    @Autowired
    private WareInfoService wareInfoService;

    /**
     * 查询仓库信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param wareInfo 仓库信息,字段选择性传入,默认为等值查询
     * @return 仓库信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:wareinfo:list")
    public BaseResult<PageResult<WareInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareInfo wareInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<WareInfo> pageResult = wareInfoService.queryPage(pageDomain, wareInfo);
        return (BaseResult<PageResult<WareInfo>>) toPageableResult(pageResult);
    }

    /**
     * 获取仓库信息详情
     *
     * @param id 仓库信息主键id
     * @return 仓库信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:wareinfo:info")
    public BaseResult<WareInfo> info(@PathVariable("id") Long id){
        WareInfo wareInfo = wareInfoService.getById(id);
        return (BaseResult<WareInfo>) toResponseResult(wareInfo);
    }

    /**
     * 新增仓库信息
     *
     * @param wareInfo 仓库信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("ware:wareinfo:save")
    public BaseResult<Boolean> save(@RequestBody WareInfo wareInfo) {
        boolean save = wareInfoService.save(wareInfo);
        return toOperationResult(save);
    }

    /**
     * 修改仓库信息
     *
     * @param wareInfo 仓库信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("ware:wareinfo:update")
    public BaseResult<Boolean> update(@RequestBody WareInfo wareInfo) {
        boolean update = wareInfoService.updateById(wareInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除仓库信息
     *
     * @param ids 仓库信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("ware:wareinfo:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = wareInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
