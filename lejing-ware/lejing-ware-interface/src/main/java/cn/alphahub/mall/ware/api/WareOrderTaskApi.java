package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存工作单-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareOrderTaskApi {

    /**
     * 查询库存工作单列表
     *
     * @param page          当前页码,默认第1页
     * @param rows          显示行数,默认10条
     * @param orderColumn   排序排序字段,默认不排序
     * @param isAsc         排序方式,desc或者asc
     * @param wareOrderTask 库存工作单, 查询字段选择性传入, 默认为等值查询
     * @return 库存工作单分页数据
     */
    @GetMapping("ware/wareordertask/list")
    Result<PageResult<WareOrderTask>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareOrderTask wareOrderTask
    );

    /**
     * 获取库存工作单详情
     *
     * @param id 库存工作单主键id
     * @return 库存工作单详细信息
     */
    @GetMapping("ware/wareordertask/info/{id}")
    Result<WareOrderTask> info(@PathVariable("id") Long id);

    /**
     * 新增库存工作单
     *
     * @param wareOrderTask 库存工作单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("ware/wareordertask/save")
    Result<Boolean> save(@RequestBody WareOrderTask wareOrderTask);

    /**
     * 修改库存工作单
     *
     * @param wareOrderTask 库存工作单, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("ware/wareordertask/update")
    Result<Boolean> update(@RequestBody WareOrderTask wareOrderTask);

    /**
     * 批量删除库存工作单
     *
     * @param ids 库存工作单id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("ware/wareordertask/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
