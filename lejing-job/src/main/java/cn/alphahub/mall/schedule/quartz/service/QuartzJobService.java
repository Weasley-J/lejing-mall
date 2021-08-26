package cn.alphahub.mall.schedule.quartz.service;

import cn.alphahub.mall.schedule.quartz.entity.QuartzBean;
import org.quartz.JobKey;

import java.util.List;

/**
 * Quartz任务调度上层接口
 *
 * @author lwj
 */
public interface QuartzJobService {
    /**
     * 创建定时任务Simple
     * quartzBean.getInterval()==null表示单次提醒，
     * 否则循环提醒（quartzBean.getEndTime()!=null）
     *
     * @param quartzBean Quartz参数实体类
     * @return true：成功，false：失败
     */
    Boolean createScheduleJobSimple(QuartzBean quartzBean);

    /**
     * 创建定时任务Cron
     * 定时任务创建之后默认启动状态
     *
     * @param quartzBean 定时任务信息类
     * @return true：成功，false：失败
     */
    Boolean createScheduleJobCron(QuartzBean quartzBean);

    /**
     * 根据任务名称暂停定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    Boolean pauseScheduleJob(String jobName, String jobGroup);

    /**
     * 根据任务名称恢复定时任务
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  定时任务名
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败
     */
    Boolean resumeScheduleJob(String jobName, String jobGroup);

    /**
     * 根据任务名称立即运行一次定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    Boolean runOnce(String jobName, String jobGroup);

    /**
     * 更新定时任务Simple
     *
     * @param quartzBean 定时任务信息类
     */
    void updateScheduleJobSimple(QuartzBean quartzBean);

    /**
     * 更新定时任务Cron
     *
     * @param quartzBean 定时任务信息类
     */
    Boolean updateScheduleJobCron(QuartzBean quartzBean);

    /**
     * 根据定时任务名称从调度器当中删除定时任务
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     * @return true：成功，false：失败@return true：成功，false：失败
     */
    Boolean deleteScheduleJob(String jobName, String jobGroup);

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
    String getScheduleJobStatus(String jobName, String jobGroup);

    /**
     * 根据定时任务名称来判断任务是否存在
     *
     * @param jobName  定时任务名称
     * @param jobGroup 任务组（没有分组传值null）
     */
    Boolean checkExistsScheduleJob(String jobName, String jobGroup);

    /**
     * 根据任务組刪除定時任务
     *
     * @param jobGroup 任务组
     */
    Boolean deleteGroupJob(String jobGroup);

    /**
     * 根据任务組批量刪除定時任务
     *
     * @param jobKeyList
     */
    Boolean batchDeleteGroupJob(List<JobKey> jobKeyList);

    /**
     * 根据任务組批量查询出jobKey
     *
     * @param jobGroup 任务组
     */
    void batchQueryGroupJob(List<JobKey> jobKeyList, String jobGroup);
}
