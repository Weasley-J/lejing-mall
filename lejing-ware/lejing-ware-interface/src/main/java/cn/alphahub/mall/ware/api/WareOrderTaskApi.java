package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.WareOrderTask;
import org.springframework.web.bind.annotation.*;

/**
 * 库存工作单-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RequestMapping("ware/wareordertask")
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
    @GetMapping("/list")
    BaseResult<PageResult<WareOrderTask>> list(
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
    @GetMapping("/info/{id}")
    BaseResult<WareOrderTask> info(@PathVariable("id") Long id);

    /**
     * 新增库存工作单
     *
     * @param wareOrderTask 库存工作单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody WareOrderTask wareOrderTask);

    /**
     * 修改库存工作单
     *
     * @param wareOrderTask 库存工作单, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody WareOrderTask wareOrderTask);

    /**
     * 批量删除库存工作单
     *
     * @param ids 库存工作单id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
