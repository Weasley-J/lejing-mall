package cn.alphahub.mall.schedule.quartz.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.job.domain.SysJob;
import cn.alphahub.mall.schedule.job.service.SysJobService;
import cn.alphahub.mall.schedule.quartz.service.QuartzJobService;
import cn.alphahub.mall.schedule.quartz.util.CronUtil;
import cn.alphahub.mall.schedule.quartz.util.JobBeanUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时任务调度Controller
 *
 * @author lwj
 */
@RestController(value = "sysJobControllerBak")
@RequestMapping("/sys/job")
public class SysJobController {

    @Autowired
    private SysJobService jobService;
    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 分页查询定时任务
     *
     * @param page 当前页
     * @param rows 每页大小
     * @return 分页定时任务
     */
    @GetMapping(value = "list")
    public BaseResult<PageResult<SysJob>> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                               @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        PageResult<SysJob> result = new PageResult<>();
        result.startPage(new PageDomain(page, rows, null, null));
        List<SysJob> jobs = jobService.list();
        result.getPage(jobs);
        return BaseResult.ok(result);
    }

    /**
     * 获取定时任务详细信息
     *
     * @param jobId 任务id
     * @return 定时任务详情
     */
    @GetMapping(value = "/{jobId}")
    public BaseResult<SysJob> getInfo(@PathVariable("jobId") Long jobId) {
        SysJob sysJob = jobService.getById(jobId);
        return BaseResult.ok(sysJob);
    }

    /**
     * 新增定时任务(创建->启动定时任务)
     *
     * @param sysJob 定时任务元数据
     * @return success/error
     */
    @PostMapping
    public BaseResult<Boolean> add(@RequestBody SysJob sysJob) {
        if (!CronUtil.isValid(sysJob.getCronExpression())) {
            return BaseResult.error("cron表达式不正确");
        }
        quartzJobService.createScheduleJobCron(JobBeanUtil.getQuartzBean(sysJob));
        boolean save = jobService.save(sysJob);
        return BaseResult.ok(save);
    }

    /**
     * 删除定时任务
     *
     * @param jobIds 定时任务id集合
     */
    @DeleteMapping("/ids/{jobIds}")
    public BaseResult<Void> remove(@PathVariable("jobIds") Long[] jobIds) {
        for (Long id : jobIds) {
            SysJob sysJob = jobService.getById(id);
            String jobName = sysJob.getJobName();
            String jobGroup = sysJob.getJobGroup();
            quartzJobService.deleteScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
            jobService.removeById(id);
        }
        return BaseResult.success();
    }

    /**
     * 删除定时任务
     *
     * @param jobId 定时任务id
     * @return
     */
    @DeleteMapping("/id/{jobId}")
    public BaseResult<Boolean> remove(@PathVariable("jobId") Long jobId) {
        SysJob sysJob = jobService.getById(jobId);
        if (ObjectUtils.isEmpty(sysJob)) {
            return BaseResult.error();
        }
        String jobName = sysJob.getJobName();
        String jobGroup = sysJob.getJobGroup();
        quartzJobService.deleteScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        boolean deleted = jobService.removeById(jobId);
        return BaseResult.success(deleted);
    }

    /**
     * 修改定时任务
     *
     * @param sysJob 定时任务元数据
     * @return success/error
     */
    @PutMapping
    public BaseResult<Void> edit(@RequestBody SysJob sysJob) {
        if (!CronUtil.isValid(sysJob.getCronExpression())) {
            return BaseResult.error("cron表达式不正确");
        }
        quartzJobService.updateScheduleJobCron(JobBeanUtil.getQuartzBean(sysJob));
        jobService.updateById(sysJob);
        return BaseResult.success();
    }

    /**
     * 定时任务状态修改
     *
     * @param sysJob 定时任务元数据
     * @return success/error
     */
    @PutMapping("/changeStatus")
    public BaseResult<Void> changeStatus(@RequestBody SysJob sysJob) {
        SysJob newJob = jobService.getById(sysJob.getJobId());
        quartzJobService.updateScheduleJobCron(JobBeanUtil.getQuartzBean(sysJob));
        jobService.updateById(newJob);
        return BaseResult.success();
    }

    /**
     * 立即执行一次定时任务
     *
     * @param sysJob 定时任务元数据
     * @return success/error
     */
    @PutMapping("/run")
    public BaseResult<Void> run(@RequestBody SysJob sysJob) {
        SysJob newJob = jobService.getById(sysJob.getJobId());
        quartzJobService.runOnce(newJob.getJobName(), newJob.getJobGroup());
        return BaseResult.success();
    }

    /**
     * 暂停定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping(value = {"/pause/{jobName}", "/pause/{jobName}/{jobGroup}"})
    public BaseResult<Void> pauseJob(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        quartzJobService.pauseScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        return BaseResult.success();
    }

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping(value = {"/resume/{jobName}", "/resume/{jobName}/{jobGroup}"})
    public BaseResult<Void> resume(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        quartzJobService.resumeScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        return BaseResult.success();
    }

    /**
     * 根据任务名称判断定时任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @GetMapping(value = {"/check/{jobName}", "/check/{jobName}/{jobGroup}"})
    public BaseResult<Boolean> check(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        Boolean flag = quartzJobService.checkExistsScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        return BaseResult.success(flag);
    }

    /**
     * 获取任务状态
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return BLOCKED-阻塞;
     * COMPLETE-完成;
     * ERROR-出错;
     * NONE-不存在;
     * NORMAL-正常;
     * PAUSED-暂停;
     */
    @GetMapping(value = {"/status/{jobName}", "/status/{jobName}/{jobGroup}"})
    public BaseResult<String> status(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        String jobStatus = quartzJobService.getScheduleJobStatus(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
        return BaseResult.success("任务状态", jobStatus);
    }
}
