package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.ware.domain.PurchaseDetail;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 仓储采购表-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface PurchaseDetailApi {
    /**
     * 查询仓储采购表列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param purchaseDetail 仓储采购表, 查询字段选择性传入, 默认为等值查询
     * @return 仓储采购表分页数据
     */
    @GetMapping("ware/purchasedetail/list")
    Result<PageResult<PurchaseDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            PurchaseDetail purchaseDetail
    );

    /**
     * 获取仓储采购表详情
     *
     * @param id 仓储采购表主键id
     * @return 仓储采购表详细信息
     */
    @GetMapping("ware/purchasedetail/info/{id}")
    Result<PurchaseDetail> info(@PathVariable("id") Long id);

    /**
     * 新增仓储采购表
     *
     * @param purchaseDetail 仓储采购表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("ware/purchasedetail/save")
    Result<Boolean> save(@RequestBody PurchaseDetail purchaseDetail);

    /**
     * 修改仓储采购表
     *
     * @param purchaseDetail 仓储采购表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("ware/purchasedetail/update")
    Result<Boolean> update(@RequestBody PurchaseDetail purchaseDetail);

    /**
     * 批量删除仓储采购表
     *
     * @param ids 仓储采购表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("ware/purchasedetail/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
