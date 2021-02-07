package cn.alphahub.mall.ware.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.ware.domain.UndoLog;
import cn.alphahub.mall.ware.service.UndoLogService;

import java.util.Arrays;

/**
 * 撤销日志表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@RestController
@RequestMapping("ware/undolog")
public class UndoLogController extends BaseController {
    @Autowired
    private UndoLogService undoLogService;

    /**
     * 查询撤销日志表列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param undoLog 撤销日志表,字段选择性传入,默认为等值查询
     * @return 撤销日志表分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:undolog:list")
    public BaseResult<PageResult<UndoLog>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            UndoLog undoLog
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<UndoLog> pageResult = undoLogService.queryPage(pageDomain, undoLog);
        return (BaseResult<PageResult<UndoLog>>) toPageableResult(pageResult);
    }

    /**
     * 获取撤销日志表详情
     *
     * @param id 撤销日志表主键id
     * @return 撤销日志表详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("ware:undolog:info")
    public BaseResult<UndoLog> info(@PathVariable("id") Long id){
        UndoLog undoLog = undoLogService.getById(id);
        return (BaseResult<UndoLog>) toResponseResult(undoLog);
    }

    /**
     * 新增撤销日志表
     *
     * @param undoLog 撤销日志表元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("ware:undolog:save")
    public BaseResult<Boolean> save(@RequestBody UndoLog undoLog) {
        boolean save = undoLogService.save(undoLog);
        return toOperationResult(save);
    }

    /**
     * 修改撤销日志表
     *
     * @param undoLog 撤销日志表,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("ware:undolog:update")
    public BaseResult<Boolean> update(@RequestBody UndoLog undoLog) {
        boolean update = undoLogService.updateById(undoLog);
        return toOperationResult(update);
    }

    /**
     * 批量删除撤销日志表
     *
     * @param ids 撤销日志表id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("ware:undolog:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = undoLogService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
