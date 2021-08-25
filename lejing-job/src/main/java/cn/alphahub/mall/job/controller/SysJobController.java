package cn.alphahub.mall.job.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJob;
import cn.alphahub.mall.job.service.SysJobService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 定时任务调度表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:15:04
 */
@RestController
@RequestMapping("/job/sysjob")
public class SysJobController {
    @Resource
    private SysJobService sysJobService;

    /**
     * 查询定时任务调度表列表
     *
     * @param page   分页参数
     * @param sysJob 定时任务调度表, 查询字段选择性传入, 默认为等值查询
     * @return 定时任务调度表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysJob>> list(@ModelAttribute(name = "page") PageDomain page,
                                               @ModelAttribute(name = "sysJob") SysJob sysJob
    ) {
        PageResult<SysJob> pageResult = sysJobService.queryPage(page, sysJob);
        return BaseResult.ok(pageResult);
    }

    /**
     * 获取定时任务调度表详情
     *
     * @param jobId 定时任务调度表主键id
     * @return 定时任务调度表详细信息
     */
    @GetMapping("/info/{jobId}")
    public BaseResult<SysJob> info(@PathVariable("jobId") Long jobId) {
        SysJob sysJob = sysJobService.getById(jobId);
        return BaseResult.ok(sysJob);
    }

    /**
     * 新增定时任务调度表
     *
     * @param sysJob 定时任务调度表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SysJob sysJob) {
        boolean save = sysJobService.save(sysJob);
        return BaseResult.ok(save);
    }

    /**
     * 修改定时任务调度表
     *
     * @param sysJob 定时任务调度表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SysJob sysJob) {
        boolean update = sysJobService.updateById(sysJob);
        return BaseResult.ok(update);
    }

    /**
     * 批量删除定时任务调度表
     *
     * @param jobIds 定时任务调度表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{jobIds}")
    public BaseResult<Boolean> delete(@PathVariable(name = "jobIds") Long[] jobIds) {
        boolean delete = sysJobService.removeByIds(Arrays.asList(jobIds));
        return BaseResult.ok(delete);
    }
}
