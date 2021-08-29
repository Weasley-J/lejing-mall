package cn.alphahub.mall.schedule.convertor;

import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 任务调度Java Bean转换器
 *
 * @author lwj
 * @version 1.0
 * @date 2021/08/28
 */
@Mapper(componentModel = "spring")
public interface ScheduleConvertor {

    /**
     * ScheduleConvertor实例
     */
    //ScheduleConvertor INSTANCE = Mappers.getMapper(ScheduleConvertor.class);

    /**
     * QuartzJob -> QuartzParam
     *
     * @param job quartz定时任务调度
     * @return QuartzParam
     * @apiNote db -> service
     */
    @Mapping(target = "statusName", ignore = true)
    @Mapping(target = "startTime", ignore = true)
    @Mapping(target = "interval", ignore = true)
    @Mapping(target = "endTime", ignore = true)
    @Mapping(target = "jobDataMap", expression = "java(QuartzParam.getJobDataMap(job.getJobParams()))")
    QuartzParam toQuartzParam(QuartzJob job);

    /**
     * QuartzParam -> QuartzJob
     *
     * @param param quartz调度任务参数
     * @return QuartzJob
     */
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "updateBy", ignore = true)
    @Mapping(target = "remark", ignore = true)
    @Mapping(target = "jobParams", ignore = true)
    @Mapping(target = "isConcurrent", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "createBy", ignore = true)
    QuartzJob toQuartzJob(QuartzParam param);

    /**
     * QuartzJob -> QuartzJobDTO
     *
     * @param quartzJob 持久层quartz定时任务调度
     * @return QuartzJobDTO
     * @apiNote db -> mvc
     */
    @Mapping(target = "statusName", ignore = true)
    QuartzJobDTO toQuartzJobDto(QuartzJob quartzJob);

    /**
     * QuartzJobDTO - > QuartzJob
     *
     * @param dto 持久层quartz定时任务调度
     * @return QuartzJobDTO
     * @apiNote db -> mvc
     */
    QuartzJob toQuartzJob(QuartzJobDTO dto);
}
