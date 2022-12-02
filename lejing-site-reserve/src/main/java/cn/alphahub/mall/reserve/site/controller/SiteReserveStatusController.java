package cn.alphahub.mall.reserve.site.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.site.domain.SiteReserveStatus;
import cn.alphahub.mall.reserve.site.service.SiteReserveStatusService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地状态表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@RestController
@RequestMapping("site/sitereservestatus")
public class SiteReserveStatusController {
    @Autowired
    private SiteReserveStatusService siteReserveStatusService;

    /**
     * 查询场地状态表列表
     *
     * @param page              当前页码,默认第1页
     * @param rows              显示行数,默认10条
     * @param orderColumn       排序排序字段,默认不排序
     * @param isAsc             排序方式,desc或者asc
     * @param siteReserveStatus 场地状态表, 查询字段选择性传入, 默认为等值查询
     * @return 场地状态表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SiteReserveStatus>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SiteReserveStatus siteReserveStatus
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SiteReserveStatus> pageResult = siteReserveStatusService.queryPage(pageDomain, siteReserveStatus);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地状态表详情
     *
     * @param siteStatusId 场地状态表主键id
     * @return 场地状态表详细信息
     */
    @GetMapping("/info/{siteStatusId}")
    public Result<SiteReserveStatus> info(@PathVariable("siteStatusId") Long siteStatusId) {
        SiteReserveStatus siteReserveStatus = siteReserveStatusService.getById(siteStatusId);
        return ObjectUtils.anyNotNull(siteReserveStatus) ? Result.of(siteReserveStatus) : Result.fail();
    }

    /**
     * 新增场地状态表
     *
     * @param siteReserveStatus 场地状态表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SiteReserveStatus siteReserveStatus) {
        boolean save = siteReserveStatusService.save(siteReserveStatus);
        return Result.of(save);
    }

    /**
     * 修改场地状态表
     *
     * @param siteReserveStatus 场地状态表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SiteReserveStatus siteReserveStatus) {
        boolean update = siteReserveStatusService.updateById(siteReserveStatus);
        return Result.of(update);
    }

    /**
     * 批量删除场地状态表
     *
     * @param siteStatusIds 场地状态表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{siteStatusIds}")
    public Result<Boolean> delete(@PathVariable Long[] siteStatusIds) {
        boolean delete = siteReserveStatusService.removeByIds(Arrays.asList(siteStatusIds));
        return Result.of(delete);
    }
}
