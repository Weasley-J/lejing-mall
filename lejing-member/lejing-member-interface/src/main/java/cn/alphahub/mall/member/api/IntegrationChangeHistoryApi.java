package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.IntegrationChangeHistory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 积分变化历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface IntegrationChangeHistoryApi {

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
    @GetMapping("/member/integrationchangehistory/list")
    Result<PageResult<IntegrationChangeHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            IntegrationChangeHistory integrationChangeHistory
    );

    /**
     * 获取积分变化历史记录详情
     *
     * @param id 积分变化历史记录主键id
     * @return 积分变化历史记录详细信息
     */
    @GetMapping("/member/integrationchangehistory/info/{id}")
    Result<IntegrationChangeHistory> info(@PathVariable("id") Long id);

    /**
     * 新增积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/member/integrationchangehistory/save")
    Result<Boolean> save(@RequestBody IntegrationChangeHistory integrationChangeHistory);

    /**
     * 修改积分变化历史记录
     *
     * @param integrationChangeHistory 积分变化历史记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/member/integrationchangehistory/update")
    Result<Boolean> update(@RequestBody IntegrationChangeHistory integrationChangeHistory);

    /**
     * 批量删除积分变化历史记录
     *
     * @param ids 积分变化历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/member/integrationchangehistory/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);

}
