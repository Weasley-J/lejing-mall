package cn.alphahub.mall.schedule.quartz.demo;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 模拟Quartz定时任务所重复执行的的任务类
 * 单独使用:Job class must implement the Job interface.
 * <b>与springboot整合: Job class must abstract class QuartzJobBean,该类已经实现了Job class</b>
 */
@Slf4j
public class SpringQuartzDemo extends QuartzJobBean {

    public void print() {
        log.info("Hello, I am thread {}.", Thread.currentThread().getName());
    }

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     *
     * @param context
     * @see #execute
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap map = context.getJobDetail().getJobDataMap();
        String userId = map.getString("userId");
        log.info("Quartz定时任务: " + jobKey + ", userId: " + userId + " executing at " + new Date());
        print();
    }
}
