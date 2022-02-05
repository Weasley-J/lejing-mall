package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.GrowthChangeHistory;
import org.springframework.web.bind.annotation.*;

/**
 * 成长值变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface GrowthChangeHistoryApi {

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
    @GetMapping("/member/growthchangehistory/list")
    BaseResult<PageResult<GrowthChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            GrowthChangeHistory growthChangeHistory
    );

    /**
     * 获取成长值变化历史记录详情
     *
     * @param id 成长值变化历史记录主键id
     * @return 成长值变化历史记录详细信息
     */
    @GetMapping("/member/growthchangehistory/info/{id}")
    BaseResult<GrowthChangeHistory> info(@PathVariable("id") Long id);

    /**
     * 新增成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/member/growthchangehistory/save")
    BaseResult<Boolean> save(@RequestBody GrowthChangeHistory growthChangeHistory);

    /**
     * 修改成长值变化历史记录
     *
     * @param growthChangeHistory 成长值变化历史记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/member/growthchangehistory/update")
    BaseResult<Boolean> update(@RequestBody GrowthChangeHistory growthChangeHistory);

    /**
     * 批量删除成长值变化历史记录
     *
     * @param ids 成长值变化历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/member/growthchangehistory/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
