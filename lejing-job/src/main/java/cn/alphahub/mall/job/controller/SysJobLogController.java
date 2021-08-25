package cn.alphahub.mall.job.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJobLog;
import cn.alphahub.mall.job.service.SysJobLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 定时任务调度日志表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:15:04
 */
@RestController
@RequestMapping("/job/sysjoblog")
public class SysJobLogController {
    @Resource
    private SysJobLogService sysJobLogService;

    /**
     * 查询定时任务调度日志表列表
     *
     * @param page      分页参数
     * @param sysJobLog 定时任务调度日志表, 查询字段选择性传入, 默认为等值查询
     * @return 定时任务调度日志表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysJobLog>> list(@ModelAttribute(name = "page") PageDomain page,
                                                  @ModelAttribute(name = "sysJobLog") SysJobLog sysJobLog
    ) {
        PageResult<SysJobLog> pageResult = sysJobLogService.queryPage(page, sysJobLog);
        return BaseResult.ok(pageResult);
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
        return BaseResult.ok(sysJobLog);
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
        return BaseResult.ok(save);
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
        return BaseResult.ok(update);
    }

    /**
     * 批量删除定时任务调度日志表
     *
     * @param ids 定时任务调度日志表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable(name = "ids") Integer[] ids) {
        boolean delete = sysJobLogService.removeByIds(Arrays.asList(ids));
        return BaseResult.ok(delete);
    }
}
