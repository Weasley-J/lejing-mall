package cn.alphahub.mall.schedule.job.service;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.core.domain.QuartzParam;
import cn.alphahub.mall.schedule.job.domain.QuartzJob;
import cn.alphahub.mall.schedule.job.dto.QuartzJobDTO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * quartz定时任务调度Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-29 16:29:58
 */
public interface QuartzJobService extends IService<QuartzJob> {

    /**
     * 查询quartz定时任务调度分页列表
     *
     * @param page      分页参数
     * @param quartzJob 分页对象
     * @return quartz定时任务调度分页数据
     */
    PageResult<QuartzJob> queryPage(PageDomain page, QuartzJob quartzJob);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     * @return result
     */
    BaseResult<Boolean> createSimpleScheduleJob(QuartzParam param);

    /**
     * 更新Simple定时任务
     *
     * @param param 定时任务信息类
     * @return result
     */
    BaseResult<Boolean> updateSimpleScheduleJob(QuartzParam param);

    /**
     * 新增定时任务(创建->启动定时任务)
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    BaseResult<Boolean> save(QuartzJobDTO job);

    /**
     * 获取quartz定时任务调度详情
     *
     * @param id quartz定时任务调度主键id
     * @return quartz定时任务调度详细信息
     */
    BaseResult<QuartzJobDTO> info(Long id);

    /**
     * 删除定时任务
     *
     * @param ids 定时任务id集合
     * @return void
     */
    BaseResult<Void> remove(Long[] ids);

    /**
     * 修改定时任务
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    BaseResult<Void> edit(QuartzJobDTO job);

    /**
     * 定时任务状态修改
     *
     * @param job 定时任务元数据
     * @return success/error
     */
    BaseResult<Void> updateStatus(QuartzJobDTO job);

    /**
     * 立即执行一次定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return success/error
     */
    BaseResult<Void> runAtNow(String jobName, String jobGroup);

    /**
     * 暂停定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    BaseResult<Void> pause(String jobName, String jobGroup);

    /**
     * 恢复定时任务/继续定时任务
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    BaseResult<Void> resume(String jobName, String jobGroup);

    /**
     * 根据任务名称判断定时任务是否存在
     *
     * @param jobName  任务名
     * @param jobGroup 任务组
     * @return true：成功，false：失败
     */
    BaseResult<Boolean> check(String jobName, String jobGroup);

    /**
     * 获取任务状态
     *
     * @param jobName  任务名(自定义)
     * @param jobGroup 任务组（没有分组传值null）
     * @return 状态名称
     */
    BaseResult<String> status(String jobName, String jobGroup);
}
