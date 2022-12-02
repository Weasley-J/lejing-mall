package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.UndoLog;
import cn.alphahub.mall.coupon.service.UndoLogService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 撤销日志表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/undolog")
public class UndoLogController {
    @Resource
    private UndoLogService undoLogService;

    /**
     * 查询撤销日志表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param undoLog     撤销日志表, 查询字段选择性传入, 默认为等值查询
     * @return 撤销日志表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<UndoLog>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            UndoLog undoLog
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<UndoLog> pageResult = undoLogService.queryPage(pageDomain, undoLog);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取撤销日志表详情
     *
     * @param id 撤销日志表主键id
     * @return 撤销日志表详细信息
     */
    @GetMapping("/info/{id}")
    public Result<UndoLog> info(@PathVariable("id") Long id) {
        UndoLog undoLog = undoLogService.getById(id);
        return ObjectUtils.anyNotNull(undoLog) ? Result.of(undoLog) : Result.fail();
    }

    /**
     * 新增撤销日志表
     *
     * @param undoLog 撤销日志表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody UndoLog undoLog) {
        boolean save = undoLogService.save(undoLog);
        return Result.of(save);
    }

    /**
     * 修改撤销日志表
     *
     * @param undoLog 撤销日志表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody UndoLog undoLog) {
        boolean update = undoLogService.updateById(undoLog);
        return Result.of(update);
    }

    /**
     * 批量删除撤销日志表
     *
     * @param ids 撤销日志表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = undoLogService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
