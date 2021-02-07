package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import cn.alphahub.mall.member.service.GrowthChangeHistoryService;

import java.util.Arrays;

/**
 * 成长值变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@RestController
@RequestMapping("member/growthchangehistory")
public class GrowthChangeHistoryController extends BaseController {
    @Autowired
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 查询成长值变化历史记录列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param growthChangeHistory 成长值变化历史记录,字段选择性传入,默认为等值查询
     * @return 成长值变化历史记录分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:growthchangehistory:list")
    public BaseResult<PageResult<GrowthChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            GrowthChangeHistory growthChangeHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<GrowthChangeHistory> pageResult = growthChangeHistoryService.queryPage(pageDomain, growthChangeHistory);
        return (BaseResult<PageResult<GrowthChangeHistory>>) toPageableResult(pageResult);
    }

    /**
     * 获取成长值变化历史记录详情
     *
     * @param id 成长值变化历史记录主键id
     * @return 成长值变化历史记录详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:growthchangehistory:info")
    public BaseResult<GrowthChangeHistory> info(@PathVariable("id") Long id){
        GrowthChangeHistory growthChangeHistory = growthChangeHistoryService.getById(id);
        return (BaseResult<GrowthChangeHistory>) toResponseResult(growthChangeHistory);
    }

    /**
     * 新增成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:growthchangehistory:save")
    public BaseResult<Boolean> save(@RequestBody GrowthChangeHistory growthChangeHistory) {
        boolean save = growthChangeHistoryService.save(growthChangeHistory);
        return toOperationResult(save);
    }

    /**
     * 修改成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:growthchangehistory:update")
    public BaseResult<Boolean> update(@RequestBody GrowthChangeHistory growthChangeHistory) {
        boolean update = growthChangeHistoryService.updateById(growthChangeHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除成长值变化历史记录
     *
     * @param ids 成长值变化历史记录id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:growthchangehistory:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = growthChangeHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
