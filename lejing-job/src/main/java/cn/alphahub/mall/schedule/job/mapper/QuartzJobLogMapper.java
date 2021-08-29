package cn.alphahub.mall.schedule.job.mapper;

import cn.alphahub.mall.schedule.job.domain.QuartzJobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * quartz定时任务调度日志Mapper接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
@Mapper
public interface QuartzJobLogMapper extends BaseMapper<QuartzJobLog> {

}
