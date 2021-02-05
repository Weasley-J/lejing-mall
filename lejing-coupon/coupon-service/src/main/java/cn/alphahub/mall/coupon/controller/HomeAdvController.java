package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.domain.HomeAdv;
import cn.alphahub.mall.coupon.service.HomeAdvService;

import java.util.Arrays;

/**
 * 首页轮播广告Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@RestController
@RequestMapping("coupon/homeadv")
public class HomeAdvController extends BaseController {
    @Autowired
    private HomeAdvService homeAdvService;

    /**
     * 查询首页轮播广告列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param homeAdv 首页轮播广告,字段选择性传入,默认等值查询
     * @return 首页轮播广告分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:homeadv:list")
    public BaseResult<PageResult<HomeAdv>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            HomeAdv homeAdv
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<HomeAdv> pageResult = homeAdvService.queryPage(pageDomain, homeAdv);
        return (BaseResult<PageResult<HomeAdv>>) toPageableResult(pageResult);
    }

    /**
     * 获取首页轮播广告详情
     *
     * @param id 首页轮播广告主键id
     * @return 首页轮播广告详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:homeadv:info")
    public BaseResult<HomeAdv> info(@PathVariable("id") Long id){
        HomeAdv homeAdv = homeAdvService.getById(id);
        return (BaseResult<HomeAdv>) toResponseResult(homeAdv);
    }

    /**
     * 新增首页轮播广告
     *
     * @param homeAdv 首页轮播广告元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:homeadv:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ HomeAdv homeAdv) {
        boolean save = homeAdvService.save(homeAdv);
        return toOperationResult(save);
    }

    /**
     * 修改首页轮播广告
     *
     * @param homeAdv 首页轮播广告,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ HomeAdv homeAdv) {
        boolean update = homeAdvService.updateById(homeAdv);
        return toOperationResult(update);
    }

    /**
     * 批量删除首页轮播广告
     *
     * @param ids 首页轮播广告id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:homeadv:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = homeAdvService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
