package cn.alphahub.mall.reserve.site.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.site.domain.SiteReserve;
import cn.alphahub.mall.reserve.site.service.SiteReserveService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地预约主表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@RestController
@RequestMapping("site/sitereserve")
public class SiteReserveController {
    @Autowired
    private SiteReserveService siteReserveService;

    /**
     * 查询场地预约主表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param siteReserve 场地预约主表, 查询字段选择性传入, 默认为等值查询
     * @return 场地预约主表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SiteReserve>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SiteReserve siteReserve
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SiteReserve> pageResult = siteReserveService.queryPage(pageDomain, siteReserve);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地预约主表详情
     *
     * @param siteId 场地预约主表主键id
     * @return 场地预约主表详细信息
     */
    @GetMapping("/info/{siteId}")
    public Result<SiteReserve> info(@PathVariable("siteId") Long siteId) {
        SiteReserve siteReserve = siteReserveService.getById(siteId);
        return ObjectUtils.anyNotNull(siteReserve) ? Result.ok(siteReserve) : Result.fail();
    }

    /**
     * 新增场地预约主表
     *
     * @param siteReserve 场地预约主表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SiteReserve siteReserve) {
        boolean save = siteReserveService.save(siteReserve);
        return Result.ok(save);
    }

    /**
     * 修改场地预约主表
     *
     * @param siteReserve 场地预约主表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SiteReserve siteReserve) {
        boolean update = siteReserveService.updateById(siteReserve);
        return Result.ok(update);
    }

    /**
     * 批量删除场地预约主表
     *
     * @param siteIds 场地预约主表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{siteIds}")
    public Result<Boolean> delete(@PathVariable Long[] siteIds) {
        boolean delete = siteReserveService.removeByIds(Arrays.asList(siteIds));
        return Result.ok(delete);
    }
}
