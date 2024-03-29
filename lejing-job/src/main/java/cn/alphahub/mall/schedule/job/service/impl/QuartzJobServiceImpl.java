package cn.alphahub.mall.schedule.job.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.schedule.convertor.ScheduleConvertor;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.core.service.QuartzCoreService;
import cn.alphahub.mall.schedule.core.util.CronUtil;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import cn.alphahub.mall.schedule.job.mapper.QuartzJobMapper;
import cn.alphahub.mall.schedule.job.service.QuartzJobLogService;
import cn.alphahub.mall.schedule.job.service.QuartzJobService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.alphahub.mall.schedule.constant.ScheduleConstant.JobStatusEnum;

/**
 * quartz定时任务调度Service业务层处理
 *
 * @author Weasley J
 * @date 2021-08-29 16:29:58
 */
@Slf4j
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

    @Resource
    private QuartzCoreService quartzCoreService;

    @Resource
    private ScheduleConvertor scheduleConvertor;

    @Resource
    private QuartzJobLogService quartzJobLogService;

    @Override
    public PageResult<QuartzJob> queryPage(PageDomain page, QuartzJob quartzJob) {
        log.info("page:{},quartzJob:{}", JSONUtil.toJsonStr(page), JSONUtil.toJsonStr(quartzJob));
        PageResult<QuartzJob> pageResult = new PageResult<>();
        pageResult.startPage(page);
        List<QuartzJob> quartzJobList = this.list(Wrappers.lambdaQuery(quartzJob));
        return pageResult.getPage(quartzJobList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> createSimpleScheduleJob(QuartzParam param) {
        log.info("create-simple-schedule-job:{}", JSONUtil.toJsonStr(param));
        boolean scheduleJob = quartzCoreService.createSimpleScheduleJob(param);
        return Result.success(scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> updateSimpleScheduleJob(QuartzParam param) {
        log.info("update-simple-schedule-job:{}", JSONUtil.toJsonStr(param));
        quartzCoreService.updateSimpleScheduleJob(param);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> save(QuartzJobDTO job) {
        log.info("save:{}", JSONUtil.toJsonStr(job));
        QuartzJob quartzJob = scheduleConvertor.toQuartzJob(job);
        if (CronUtil.isInvalid(quartzJob.getCronExpression())) {
            return Result.error("cron表达式不正确");
        }
        quartzCoreService.createCronScheduleJob(scheduleConvertor.toQuartzParam(quartzJob));
        boolean save = save(quartzJob);
        return Result.of(save);
    }

    @Override
    public Result<QuartzJobDTO> info(Long id) {
        log.info("info,id:{}", id);
        QuartzJob quartzJob = getById(id);
        if (Objects.isNull(quartzJob)) {
            return Result.fail("任务不存在");
        }
        QuartzJobDTO dto = scheduleConvertor.toQuartzJobDto(quartzJob);
        String jobStatus = quartzCoreService.getScheduleJobStatus(dto.getJobName(), dto.getJobGroup());
        dto.setStatusName(jobStatus);
        return Result.of(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> remove(Long[] ids) {
        log.info("remove:{}", JSONUtil.toJsonStr(ids));
        List<QuartzJob> jobs = listByIds(Arrays.asList(ids));
        if (CollectionUtils.isEmpty(jobs)) {
            return Result.error("定时任务不存在");
        }
        List<JobKey> jobKeys = jobs.stream().map(quartzJob -> {
            QuartzParam param = scheduleConvertor.toQuartzParam(quartzJob);
            return quartzCoreService.getJobKey(param);
        }).collect(Collectors.toList());
        quartzCoreService.batchDeleteGroupJob(jobKeys);
        removeByIds(Arrays.asList(ids));
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> edit(QuartzJobDTO job) {
        log.info("edit-job:{}", JSONUtil.toJsonStr(job));
        if (CronUtil.isInvalid(job.getCronExpression())) {
            return Result.error("cron表达式不正确");
        }
        QuartzJob quartzJob = this.getById(job.getId());
        if (Objects.isNull(quartzJob)) {
            return Result.error("job'" + job.getId() + "'不存在");
        }
        quartzJob = scheduleConvertor.toQuartzJob(job);
        quartzCoreService.updateCronScheduleJob(scheduleConvertor.toQuartzParam(quartzJob));
        updateById(quartzJob);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> updateStatus(QuartzJobDTO job) {
        log.info("update-status:{}", JSONUtil.toJsonStr(job));
        QuartzJob quartzJob = this.getById(job.getId());
        if (Objects.isNull(quartzJob)) {
            return Result.error("job'" + job.getId() + "'不存在");
        }
        QuartzParam quartzParam = scheduleConvertor.toQuartzParam(quartzJob);
        if (JobStatusEnum.PAUSED.getCode() == quartzParam.getStatus()) {
            quartzCoreService.pauseScheduleJob(quartzParam.getJobName(), quartzParam.getJobGroup());
        }
        if (JobStatusEnum.NORMAL.getCode() == quartzParam.getStatus()) {
            quartzCoreService.resumeScheduleJob(quartzParam.getJobName(), quartzParam.getJobGroup());
        }
        updateById(new QuartzJob()
                .setId(job.getId())
                .setStatus(job.getStatus())
        );
        return Result.success();
    }

    @Override
    public Result<Void> runAtNow(String jobName, String jobGroup) {
        log.info("run-at-now:{},{}", jobName, jobGroup);
        quartzCoreService.executeAtNow(jobName, jobGroup);
        return Result.success();
    }

    @Override
    public Result<Void> pause(String jobName, String jobGroup) {
        log.info("pause:{},{}", jobName, jobGroup);
        quartzCoreService.pauseScheduleJob(jobName, jobGroup);
        return Result.success();
    }

    @Override
    public Result<Void> resume(String jobName, String jobGroup) {
        log.info("status:{},{}", jobName, jobGroup);
        quartzCoreService.resumeScheduleJob(jobName, jobGroup);
        return Result.success();
    }

    @Override
    public Result<Boolean> check(String jobName, String jobGroup) {
        log.info("check:{},{}", jobName, jobGroup);
        Boolean flag = quartzCoreService.isScheduleJobExists(jobName, jobGroup);
        return Result.success(flag);
    }

    @Override
    public Result<String> status(String jobName, String jobGroup) {
        log.info("status:{},{}", jobName, jobGroup);
        String jobStatus = quartzCoreService.getScheduleJobStatus(jobName, jobGroup);
        return Result.success("任务状态", jobStatus);
    }
}
