package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.HomeAdv;
import cn.alphahub.mall.coupon.service.HomeAdvService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 首页轮播广告Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/homeadv")
public class HomeAdvController {
    @Resource
    private HomeAdvService homeAdvService;

    /**
     * 查询首页轮播广告列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param homeAdv     首页轮播广告, 查询字段选择性传入, 默认为等值查询
     * @return 首页轮播广告分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<HomeAdv>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            HomeAdv homeAdv
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<HomeAdv> pageResult = homeAdvService.queryPage(pageDomain, homeAdv);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取首页轮播广告详情
     *
     * @param id 首页轮播广告主键id
     * @return 首页轮播广告详细信息
     */
    @GetMapping("/info/{id}")
    public Result<HomeAdv> info(@PathVariable("id") Long id) {
        HomeAdv homeAdv = homeAdvService.getById(id);
        return ObjectUtils.anyNotNull(homeAdv) ? Result.of(homeAdv) : Result.fail();
    }

    /**
     * 新增首页轮播广告
     *
     * @param homeAdv 首页轮播广告元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody HomeAdv homeAdv) {
        boolean save = homeAdvService.save(homeAdv);
        return Result.of(save);
    }

    /**
     * 修改首页轮播广告
     *
     * @param homeAdv 首页轮播广告, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody HomeAdv homeAdv) {
        boolean update = homeAdvService.updateById(homeAdv);
        return Result.of(update);
    }

    /**
     * 批量删除首页轮播广告
     *
     * @param ids 首页轮播广告id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = homeAdvService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
