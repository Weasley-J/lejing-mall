package cn.alphahub.mall.member.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import cn.alphahub.mall.member.service.GrowthChangeHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 成长值变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RestController
@RequestMapping("member/growthchangehistory")
public class GrowthChangeHistoryController extends BaseController {
    @Resource
    private GrowthChangeHistoryService growthChangeHistoryService;

    /**
     * 查询成长值变化历史记录列表
     *
     * @param page                当前页码,默认第1页
     * @param rows                显示行数,默认10条
     * @param orderColumn         排序排序字段,默认不排序
     * @param isAsc               排序方式,desc或者asc
     * @param growthChangeHistory 成长值变化历史记录, 查询字段选择性传入, 默认为等值查询
     * @return 成长值变化历史记录分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<GrowthChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            GrowthChangeHistory growthChangeHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<GrowthChangeHistory> pageResult = growthChangeHistoryService.queryPage(pageDomain, growthChangeHistory);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取成长值变化历史记录详情
     *
     * @param id 成长值变化历史记录主键id
     * @return 成长值变化历史记录详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<GrowthChangeHistory> info(@PathVariable("id") Long id) {
        GrowthChangeHistory growthChangeHistory = growthChangeHistoryService.getById(id);
        return ObjectUtils.anyNotNull(growthChangeHistory) ? BaseResult.ok(growthChangeHistory) : BaseResult.fail();
    }

    /**
     * 新增成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody GrowthChangeHistory growthChangeHistory) {
        boolean save = growthChangeHistoryService.save(growthChangeHistory);
        return toOperationResult(save);
    }

    /**
     * 修改成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody GrowthChangeHistory growthChangeHistory) {
        boolean update = growthChangeHistoryService.updateById(growthChangeHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除成长值变化历史记录
     *
     * @param ids 成长值变化历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = growthChangeHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
