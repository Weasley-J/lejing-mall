package cn.alphahub.mall.job.mapper;

import cn.alphahub.mall.job.domain.SysJobLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务调度日志表Mapper接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

}
