package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import cn.alphahub.mall.member.service.IntegrationChangeHistoryService;

import java.util.Arrays;

/**
 * 积分变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@RestController
@RequestMapping("member/integrationchangehistory")
public class IntegrationChangeHistoryController extends BaseController {
    @Autowired
    private IntegrationChangeHistoryService integrationChangeHistoryService;

    /**
     * 查询积分变化历史记录列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param integrationChangeHistory 积分变化历史记录,字段选择性传入,默认等值查询
     * @return 积分变化历史记录分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:integrationchangehistory:list")
    public BaseResult<PageResult<IntegrationChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            IntegrationChangeHistory integrationChangeHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<IntegrationChangeHistory> pageResult = integrationChangeHistoryService.queryPage(pageDomain, integrationChangeHistory);
        return (BaseResult<PageResult<IntegrationChangeHistory>>) toPageableResult(pageResult);
    }

    /**
     * 获取积分变化历史记录详情
     *
     * @param id 积分变化历史记录主键id
     * @return 积分变化历史记录详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:integrationchangehistory:info")
    public BaseResult<IntegrationChangeHistory> info(@PathVariable("id") Long id){
        IntegrationChangeHistory integrationChangeHistory = integrationChangeHistoryService.getById(id);
        return (BaseResult<IntegrationChangeHistory>) toResponseResult(integrationChangeHistory);
    }

    /**
     * 新增积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:integrationchangehistory:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ IntegrationChangeHistory integrationChangeHistory) {
        boolean save = integrationChangeHistoryService.save(integrationChangeHistory);
        return toOperationResult(save);
    }

    /**
     * 修改积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ IntegrationChangeHistory integrationChangeHistory) {
        boolean update = integrationChangeHistoryService.updateById(integrationChangeHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除积分变化历史记录
     *
     * @param ids 积分变化历史记录id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:integrationchangehistory:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = integrationChangeHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
