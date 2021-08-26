package cn.alphahub.mall.schedule.quartz.controller;

import cn.alphahub.mall.schedule.quartz.entity.QuartzBean;
import cn.alphahub.mall.schedule.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Quartz定时任务Controller
 *
 * @author liuwening
 */
@Slf4j
@RestController
@RequestMapping("/schedule/job")
public class QuartzController {

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 创建/启动定时任务
     *
     * @return true：成功，false：失败
     */
    @PostMapping("/startSimpleJob")
    public Boolean startSimpleJob(@RequestBody QuartzBean quartzBean) {
        JobDataMap map = new JobDataMap();
        map.put("userId", "123456");
        quartzBean.setJobDataMap(map);
        return quartzJobService.createScheduleJobSimple(quartzBean);
    }

    /**
     * 创建cron表达式的定时任务
     *
     * @param quartzBean Quartz参数实体类
     * @return true：成功，false：失败
     */
    @PostMapping("/createCronJob")
    public Boolean createJob(@RequestBody QuartzBean quartzBean) {
        return quartzJobService.createScheduleJobCron(quartzBean);
    }

    /**
     * 暂停定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping(value = {"/pauseJob/{jobName}", "/pauseJob/{jobName}/{jobGroup}"})
    public Boolean pauseJob(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        return quartzJobService.pauseScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
    }

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @PutMapping(value = {"/resume/{jobName}", "/resume/{jobName}/{jobGroup}"})
    public Boolean resume(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        return quartzJobService.resumeScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
    }

    /**
     * 删除定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @DeleteMapping(value = {"/delete/{jobName}", "/delete/{jobName}/{jobGroup}"})
    public Boolean delete(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        return quartzJobService.deleteScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
    }

    /**
     * 根据定时任务名称来判断任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    @GetMapping(value = {"/check/{jobName}", "/check/{jobName}/{jobGroup}"})
    public Boolean check(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        return quartzJobService.checkExistsScheduleJob(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
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
    public String status(@PathVariable("jobName") String jobName, @PathVariable(required = false) String jobGroup) {
        return quartzJobService.getScheduleJobStatus(jobName, ObjectUtils.isNotEmpty(jobGroup) ? jobGroup : null);
    }
}
