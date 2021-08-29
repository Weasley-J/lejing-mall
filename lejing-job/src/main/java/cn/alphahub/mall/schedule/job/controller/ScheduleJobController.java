package cn.alphahub.mall.schedule.job.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import cn.alphahub.mall.schedule.job.dto.request.SimpleScheduleJobRequest;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * quartz定时任务调度Controller
 *
 * @author Weasley J
 * @date 2021-08-29 16:29:58
 */
@RestController
@RequestMapping("/schedule/task")
public class ScheduleJobController {

    @Resource
    private QuartzCoreService quartzCoreService;

    @Resource
    private QuartzJobService quartzJobService;

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
    public BaseResult<PageResult<QuartzJob>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                  @RequestParam(value = "rows", defaultValue = "10", required = false) Integer rows
    ) {
        PageResult<QuartzJob> pageResult = quartzJobService.queryPage(new PageDomain(page, rows, null, null), null);
        return BaseResult.ok(pageResult);
    }

    /**
     * 新增cron定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody @Validated QuartzJobDTO job) {
        return quartzJobService.save(job);
    }

    /**
     * 获取定时任务详情
     *
     * @param id quartz定时任务调度主键id
     * @return quartz定时任务调度详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<QuartzJobDTO> info(@PathVariable("id") Long id) {
        return quartzJobService.info(id);
    }

    /**
     * 修改定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    @PutMapping("/edit")
    public BaseResult<Void> edit(@RequestBody @Validated QuartzJobDTO job) {
        return quartzJobService.edit(job);
    }

    /**
     * 删除定时任务
     *
     * @param ids 定时任务id集合
     */
    @DeleteMapping("/remove/{ids}")
    public BaseResult<Void> remove(@PathVariable("ids") Long[] ids) {
        return quartzJobService.remove(ids);
    }

    /**
     * 修改定时任务状态
     *
     * @param jobId  任务ID
     * @param status 任务状态: 1 正常, 0 暂停
     * @return success/error
     */
    @PutMapping("/update/status")
    public BaseResult<Void> updateStatus(@RequestParam(name = "jobId") Long jobId,
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
    @PutMapping("/run/at/now/{jobName}/{jobGroup}")
    public BaseResult<Void> runAtNow(@PathVariable(name = "jobName") String jobName,
                                     @PathVariable(name = "jobGroup", required = false) String jobGroup) {
        return quartzJobService.runAtNow(jobName, jobGroup);
    }

    /**
     * 暂停定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping("/pause/{jobName}/{jobGroup}")
    public BaseResult<Void> pause(@PathVariable(name = "jobName") String jobName,
                                  @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.pause(jobName, jobGroup);
    }

    /**
     * 恢复定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping("/resume/{jobName}/{jobGroup}")
    public BaseResult<Void> resume(@PathVariable(name = "jobName") String jobName,
                                   @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.resume(jobName, jobGroup);
    }

    /**
     * 任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @GetMapping("/check/{jobName}/{jobGroup}")
    public BaseResult<Boolean> check(@PathVariable(name = "jobName") String jobName,
                                     @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        return quartzJobService.check(jobName, jobGroup);
    }

    /**
     * 获取任务状态
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return 状态名称
     */
    @GetMapping("/status/{jobName}/{jobGroup}")
    public BaseResult<String> status(@PathVariable(name = "jobName") String jobName,
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
    public BaseResult<Boolean> deleteFromScheduleFactory(@PathVariable(name = "jobName") String jobName,
                                                         @PathVariable(name = "jobGroup", required = false) String jobGroup
    ) {
        boolean deleteScheduleJob = quartzCoreService.deleteScheduleJob(jobName, jobGroup);
        return BaseResult.ok(deleteScheduleJob);
    }

    /**
     * 创建简单定时任务（不持久化到db）
     * <ul>
     *     <li>创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束</li>
     * </ul>
     *
     * @param request 简单的调度任务 - 请求参数
     * @return ok
     */
    @PostMapping("/create/simple/job")
    public BaseResult<Boolean> createSimpleJob(@RequestBody @Validated SimpleScheduleJobRequest request) {
        QuartzParam param = scheduleConvertor.toQuartzParam(request);
        return quartzJobService.createSimpleScheduleJob(param);
    }

    /**
     * 更新简单定时任务（不持久化到db）
     * <ul>
     *     <li>创建简单的调度任务：从什么时间开始，循环间隔多少分钟，什么时间结束</li>
     * </ul>
     *
     * @param request 简单的调度任务 - 请求参数
     * @return ok
     */
    @PutMapping("/update/simple/job")
    public BaseResult<Boolean> updateSimpleJob(@RequestBody @Validated SimpleScheduleJobRequest request) {
        QuartzParam param = scheduleConvertor.toQuartzParam(request);
        return quartzJobService.updateSimpleScheduleJob(param);
    }
}
