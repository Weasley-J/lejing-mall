package cn.alphahub.mall.schedule.job.module.seckill;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.mall.schedule.feign.seckill.SeckillClient;
import cn.hutool.json.JSONUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Job - 上架最近三天的秒杀商品
 * <p>
 *     <ul>
 *         <li>dev: cron = "0 0/1 * ? * *"</li>
 *         <li>prod: cron = "0 0 3 ? * *"</li>
 *     </ul>
 * </p>
 *
 * @author lwj
 * @version 1.0
 * @date 2021-08-30 19:47
 */
@Component
public class ScheduleOnShelveSeckillSkuLatest3Days extends QuartzJobBean {

    @Resource
    private SeckillClient seckillClient;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        BaseResult<Void> result = seckillClient.onShelveSeckillSkuLatest3Days();
        System.err.println("任务调度上架最近三天的秒杀商品:'" + JSONUtil.toJsonStr(result) + "'");
    }
}
