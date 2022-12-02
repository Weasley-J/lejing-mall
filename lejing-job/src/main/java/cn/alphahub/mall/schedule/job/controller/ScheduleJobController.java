package cn.alphahub.mall.schedule.job.controller;

import cn.alphahub.common.annotations.Syslog;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.valid.group.Edit;
import cn.alphahub.common.valid.group.Insert;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import cn.alphahub.mall.schedule.job.dto.request.SimpleScheduleJobRequest;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Quartz定时任务调度Controller
 *
 * @author Weasley J
 * @version 1.0.1
 * @date 2021-08-29 16:29:58
 */
@Slf4j
@RestController
@RequestMapping("/schedule/job")
public class ScheduleJobController {

    /**
     * quartz任务调度核心方法
     */
    @Resource
    private QuartzCoreService quartzCoreService;

    /**
     * quartz定时任务调度业务Service
     */
    @Resource
    private QuartzJobService quartzJobService;

    /**
     * Java Bean转换
     */
    @Resource
    private ScheduleConvertor scheduleConvertor;

    /**
     * 获取时任务列表
     *
     * @param page 当前页
     * @param rows 每页大小
     * @return 定时任务分页列表
     */
    @GetMapping(value = "list")
    public Result<PageResult<QuartzJob>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                              @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows
    ) {
        PageResult<QuartzJob> pageResult = quartzJobService.queryPage(new PageDomain(page, rows, null, null), null);
        return Result.ok(pageResult);
    }

    /**
     * 新增cron定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody @Validated({Insert.class}) QuartzJobDTO job) {
        return quartzJobService.save(job);
    }

    /**
     * 获取定时任务详情
     *
     * @param id quartz定时任务调度主键id
     * @return quartz定时任务调度详细信息
     */
    @GetMapping("/info/{id}")
    public Result<QuartzJobDTO> info(@PathVariable("id") Long id) {
        return quartzJobService.info(id);
    }

    /**
     * 更新定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    @PutMapping("/edit")
    public Result<Void> edit(@RequestBody @Validated({Edit.class}) QuartzJobDTO job) {
        return quartzJobService.edit(job);
    }

    /**
     * 删除定时任务
     *
     * @param ids 定时任务id集合
     */
    @DeleteMapping("/remove/{ids}")
    public Result<Void> remove(@PathVariable("ids") Long[] ids) {
        return quartzJobService.remove(ids);
    }

    /**
     * 修改定时任务状态
     *
     * @param jobId  任务ID
     * @param status 任务状态: 1 正常(恢复任务), 0 暂停(暂停任务)
     * @return success/error
     */
    @PutMapping("/update/status")
    public Result<Void> updateStatus(@RequestParam(name = "jobId") Long jobId,
                                     @RequestParam(name = "status") Integer status
    ) {
        return quartzJobService.updateStatus(new QuartzJobDTO()
                .setId(jobId)
                .setStatus(status)
        );
    }

    /**
     * 立即执行一次任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return success/error
     */
    @Syslog(name = "立即执行一次")
    @PutMapping("/run/at/now/{jobName}/{jobGroup}")
    public Result<Void> runAtNow(@PathVariable(name = "jobName") String jobName,
                                 @PathVariable(name = "jobGroup", required = false) String jobGroup) {
        return quartzJobService.runAtNow(jobName, jobGroup);
    }

    /**
     * 暂停全部任务
     *
     * @return result
     */
    @PutMapping("/pause/all")
    public Result<String> pauseAll() {
        try {
            quartzCoreService.pauseAll();
            return Result.ok("暂停全部任务成功");
        } catch (SchedulerException e) {
            log.error("scheduler-exception:{}", e.getMessage(), e);
            return Result.fail("暂停全部任务失败:" + e.getLocalizedMessage());
        }
    }

    /**
     * 恢复全部任务
     *
     * @return result
     */
    @PutMapping("/resume/all")
    public Result<String> resumeAll() {
        try {
            quartzCoreService.resumeAll();
            return Result.ok("恢复全部任务成功");
        } catch (SchedulerException e) {
            log.error("scheduler-exception:{}", e.getMessage(), e);
            return Result.fail("恢复全部任务失败:" + e.getLocalizedMessage());
        }
    }

    /**
     * 暂停单个定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @Syslog(name = "暂停单个定时任务")
    @PutMapping("/pause/one/{jobName}/{jobGroup}")
    public Result<Void> pause(@PathVariable(name = "jobName") String jobName,
                              @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.pause(jobName, jobGroup);
    }

    /**
     * 恢复单个定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @Syslog(name = "恢复单个定时任务")
    @PutMapping("/resume/one/{jobName}/{jobGroup}")
    public Result<Void> resume(@PathVariable(name = "jobName") String jobName,
                               @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.resume(jobName, jobGroup);
    }

    /**
     * 检查任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @GetMapping("/check/{jobName}/{jobGroup}")
    public Result<Boolean> check(@PathVariable(name = "jobName") String jobName,
                                 @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.check(jobName, jobGroup);
    }

    /**
     * 获取任务状态信息
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return 状态名称
     */
    @GetMapping("/status/{jobName}/{jobGroup}")
    public Result<String> status(@PathVariable(name = "jobName") String jobName,
                                 @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.status(jobName, jobGroup);
    }

    /**
     * 从调度器中删除任务
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return 状态名称
     */
    @GetMapping("/delete/{jobName}/{jobGroup}")
    public Result<Boolean> deleteFromScheduleFactory(@PathVariable(name = "jobName") String jobName,
                                                     @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        boolean deleteScheduleJob = quartzCoreService.deleteScheduleJob(jobName, jobGroup);
        return Result.ok(deleteScheduleJob);
    }

    /**
     * 创建简单定时任务
     * <ul>
     *     <li>不持久化到[业务数据库]</li>
     *     <li>创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束</li>
     * </ul>
     *
     * @param request 简单的调度任务 - 请求参数
     * @return ok
     */
    @PostMapping("/create/simple/job")
    public Result<Boolean> createSimpleJob(@RequestBody @Validated({Insert.class}) SimpleScheduleJobRequest request) {
        QuartzParam param = scheduleConvertor.toQuartzParam(request);
        return quartzJobService.createSimpleScheduleJob(param);
    }

    /**
     * 更新简单定时任务
     * <ul>
     *     <li>不持久化到[业务数据库]</li>
     *     <li>创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束</li>
     * </ul>
     *
     * @param request 简单的调度任务 - 请求参数
     * @return ok
     */
    @PutMapping("/update/simple/job")
    public Result<Boolean> updateSimpleJob(@RequestBody @Validated({Edit.class}) SimpleScheduleJobRequest request) {
        QuartzParam param = scheduleConvertor.toQuartzParam(request);
        return quartzJobService.updateSimpleScheduleJob(param);
    }
}
