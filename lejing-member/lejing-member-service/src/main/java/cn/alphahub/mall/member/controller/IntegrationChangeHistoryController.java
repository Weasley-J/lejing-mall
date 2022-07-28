package cn.alphahub.mall.member.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import cn.alphahub.mall.member.service.IntegrationChangeHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 积分变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RestController
@RequestMapping("member/integrationchangehistory")
public class IntegrationChangeHistoryController {
    @Resource
    private IntegrationChangeHistoryService integrationChangeHistoryService;

    /**
     * 查询积分变化历史记录列表
     *
     * @param page                     当前页码,默认第1页
     * @param rows                     显示行数,默认10条
     * @param orderColumn              排序排序字段,默认不排序
     * @param isAsc                    排序方式,desc或者asc
     * @param integrationChangeHistory 积分变化历史记录, 查询字段选择性传入, 默认为等值查询
     * @return 积分变化历史记录分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<IntegrationChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            IntegrationChangeHistory integrationChangeHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<IntegrationChangeHistory> pageResult = integrationChangeHistoryService.queryPage(pageDomain, integrationChangeHistory);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取积分变化历史记录详情
     *
     * @param id 积分变化历史记录主键id
     * @return 积分变化历史记录详细信息
     */
    @GetMapping("/info/{id}")
    public Result<IntegrationChangeHistory> info(@PathVariable("id") Long id) {
        IntegrationChangeHistory integrationChangeHistory = integrationChangeHistoryService.getById(id);
        return ObjectUtils.anyNotNull(integrationChangeHistory) ? Result.ok(integrationChangeHistory) : Result.fail();
    }

    /**
     * 新增积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody IntegrationChangeHistory integrationChangeHistory) {
        boolean save = integrationChangeHistoryService.save(integrationChangeHistory);
        return Result.ok(save);
    }

    /**
     * 修改积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody IntegrationChangeHistory integrationChangeHistory) {
        boolean update = integrationChangeHistoryService.updateById(integrationChangeHistory);
        return Result.ok(update);
    }

    /**
     * 批量删除积分变化历史记录
     *
     * @param ids 积分变化历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = integrationChangeHistoryService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
