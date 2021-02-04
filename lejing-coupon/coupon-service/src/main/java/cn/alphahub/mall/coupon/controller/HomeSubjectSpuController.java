package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.domain.HomeSubjectSpu;
import cn.alphahub.mall.coupon.service.HomeSubjectSpuService;

import java.util.Arrays;

/**
 * 专题商品Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@RestController
@RequestMapping("coupon/homesubjectspu")
public class HomeSubjectSpuController extends BaseController {
    @Autowired
    private HomeSubjectSpuService homeSubjectSpuService;

    /**
     * 查询专题商品列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param homeSubjectSpu 专题商品,字段选择性传入,默认等值查询
     * @return 专题商品分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("coupon:homesubjectspu:list")
    public BaseResult<PageResult<HomeSubjectSpu>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            HomeSubjectSpu homeSubjectSpu
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<HomeSubjectSpu> pageResult = homeSubjectSpuService.queryPage(pageDomain, homeSubjectSpu);
        return (BaseResult<PageResult<HomeSubjectSpu>>) toPageableResult(pageResult);
    }

    /**
     * 获取专题商品详情
     *
     * @param id 专题商品主键id
     * @return 专题商品详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<HomeSubjectSpu> info(@PathVariable("id") Long id){
        HomeSubjectSpu homeSubjectSpu = homeSubjectSpuService.getById(id);
        return (BaseResult<HomeSubjectSpu>) toResponseResult(homeSubjectSpu);
    }

    /**
     * 新增专题商品
     *
     * @param homeSubjectSpu 专题商品元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:homesubjectspu:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ HomeSubjectSpu homeSubjectSpu) {
        boolean save = homeSubjectSpuService.save(homeSubjectSpu);
        return toOperationResult(save);
    }

    /**
     * 修改专题商品
     *
     * @param homeSubjectSpu 专题商品,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ HomeSubjectSpu homeSubjectSpu) {
        boolean update = homeSubjectSpuService.updateById(homeSubjectSpu);
        return toOperationResult(update);
    }

    /**
     * 批量删除专题商品
     *
     * @param ids 专题商品id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:homesubjectspu:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = homeSubjectSpuService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}