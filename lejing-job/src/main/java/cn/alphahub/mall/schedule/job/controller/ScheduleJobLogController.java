package cn.alphahub.mall.schedule.job.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.job.domain.QuartzJobLog;
import cn.alphahub.mall.schedule.job.service.QuartzJobLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * quartz定时任务调度日志Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
@RestController
@RequestMapping("/job/quartzjoblog")
public class ScheduleJobLogController {
    @Resource
    private QuartzJobLogService quartzJobLogService;

    /**
     * 查询quartz定时任务调度日志列表
     *
     * @param page         分页参数
     * @param quartzJobLog quartz定时任务调度日志, 查询字段选择性传入, 默认为等值查询
     * @return quartz定时任务调度日志分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<QuartzJobLog>> list(@ModelAttribute(name = "page") PageDomain page,
                                                     @ModelAttribute(name = "quartzJobLog") QuartzJobLog quartzJobLog
    ) {
        PageResult<QuartzJobLog> pageResult = quartzJobLogService.queryPage(page, quartzJobLog);
        return BaseResult.ok(pageResult);
    }

    /**
     * 获取quartz定时任务调度日志详情
     *
     * @param id quartz定时任务调度日志主键id
     * @return quartz定时任务调度日志详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<QuartzJobLog> info(@PathVariable("id") Long id) {
        QuartzJobLog quartzJobLog = quartzJobLogService.getById(id);
        return BaseResult.ok(quartzJobLog);
    }

    /**
     * 新增quartz定时任务调度日志
     *
     * @param quartzJobLog quartz定时任务调度日志元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody QuartzJobLog quartzJobLog) {
        boolean save = quartzJobLogService.save(quartzJobLog);
        return BaseResult.ok(save);
    }

    /**
     * 修改quartz定时任务调度日志
     *
     * @param quartzJobLog quartz定时任务调度日志, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody QuartzJobLog quartzJobLog) {
        boolean update = quartzJobLogService.updateById(quartzJobLog);
        return BaseResult.ok(update);
    }

    /**
     * 批量删除quartz定时任务调度日志
     *
     * @param ids quartz定时任务调度日志id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable(name = "ids") Long[] ids) {
        boolean delete = quartzJobLogService.removeByIds(Arrays.asList(ids));
        return BaseResult.ok(delete);
    }
}
