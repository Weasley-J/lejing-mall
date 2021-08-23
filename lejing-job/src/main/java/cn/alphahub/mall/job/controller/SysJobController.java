package cn.alphahub.mall.job.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.job.domain.SysJob;
import cn.alphahub.mall.job.service.SysJobService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 定时任务调度表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:18:33
 */
@RestController
@RequestMapping("job/sysjob")
public class SysJobController extends BaseController {
    @Resource
    private SysJobService sysJobService;

    /**
     * 查询定时任务调度表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysJob      定时任务调度表, 查询字段选择性传入, 默认为等值查询
     * @return 定时任务调度表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysJob>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysJob sysJob
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysJob> pageResult = sysJobService.queryPage(pageDomain, sysJob);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
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
        return ObjectUtils.anyNotNull(sysJob) ? BaseResult.ok(sysJob) : BaseResult.fail();
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
        return toOperationResult(save);
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
        return toOperationResult(update);
    }

    /**
     * 批量删除定时任务调度表
     *
     * @param jobIds 定时任务调度表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{jobIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] jobIds) {
        boolean delete = sysJobService.removeByIds(Arrays.asList(jobIds));
        return toOperationResult(delete);
    }
}
