package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.ware.domain.Purchase;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 采购信息-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface PurchaseApi {

    /**
     * 查询采购信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param purchase    采购信息, 查询字段选择性传入, 默认为等值查询
     * @return 采购信息分页数据
     */
    @GetMapping("ware/purchase/list")
    Result<PageResult<Purchase>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Purchase purchase
    );

    /**
     * 获取采购信息详情
     *
     * @param id 采购信息主键id
     * @return 采购信息详细信息
     */
    @GetMapping("ware/purchase/info/{id}")
    Result<Purchase> info(@PathVariable("id") Long id);

    /**
     * 新增采购信息
     *
     * @param purchase 采购信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("ware/purchase/save")
    Result<Boolean> save(@RequestBody Purchase purchase);

    /**
     * 修改采购信息
     *
     * @param purchase 采购信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("ware/purchase/update")
    Result<Boolean> update(@RequestBody Purchase purchase);

    /**
     * 批量删除采购信息
     *
     * @param ids 采购信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("ware/purchase/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
