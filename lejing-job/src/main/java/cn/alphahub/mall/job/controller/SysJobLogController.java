package cn.alphahub.mall.job.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJobLog;
import cn.alphahub.mall.job.service.SysJobLogService;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 定时任务调度日志表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
@RestController
@RequestMapping("job/sysjoblog")
public class SysJobLogController extends BaseController {
    @Resource
    private SysJobLogService sysJobLogService;

    /**
     * 查询定时任务调度日志表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysJobLog   定时任务调度日志表, 查询字段选择性传入, 默认为等值查询
     * @return 定时任务调度日志表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysJobLog>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysJobLog sysJobLog
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysJobLog> pageResult = sysJobLogService.queryPage(pageDomain, sysJobLog);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取定时任务调度日志表详情
     *
     * @param id 定时任务调度日志表主键id
     * @return 定时任务调度日志表详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SysJobLog> info(@PathVariable("id") Integer id) {
        SysJobLog sysJobLog = sysJobLogService.getById(id);
        return ObjectUtils.anyNotNull(sysJobLog) ? BaseResult.ok(sysJobLog) : BaseResult.fail();
    }

    /**
     * 新增定时任务调度日志表
     *
     * @param sysJobLog 定时任务调度日志表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SysJobLog sysJobLog) {
        boolean save = sysJobLogService.save(sysJobLog);
        return toOperationResult(save);
    }

    /**
     * 修改定时任务调度日志表
     *
     * @param sysJobLog 定时任务调度日志表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SysJobLog sysJobLog) {
        boolean update = sysJobLogService.updateById(sysJobLog);
        return toOperationResult(update);
    }

    /**
     * 批量删除定时任务调度日志表
     *
     * @param ids 定时任务调度日志表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Integer[] ids) {
        boolean delete = sysJobLogService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
