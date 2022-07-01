package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品库存-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
public interface WareSkuApi {
    /**
     * 下单锁定库存
     *
     * @param skuLockVo 锁定库存
     * @return 库存锁定结果
     */
    @PostMapping("ware/waresku/order/lock/stock")
    Result<LockStockResultTo> orderLockStock(@RequestBody WareSkuLockVo skuLockVo);

    /**
     * 查看是否有库存
     *
     * @param skuIds sku id 集合
     * @return 商品库存列表
     */
    @PostMapping("ware/waresku/skuHasStock")
    Result<List<WareSkuVO>> getSkuHasStock(@RequestBody List<Long> skuIds);

    /**
     * 查询商品库存列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param wareSku     商品库存, 查询字段选择性传入, 默认为等值查询
     * @return 商品库存分页数据
     */
    @GetMapping("ware/waresku/list")
    Result<PageResult<WareSku>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareSku wareSku
    );

    /**
     * 查询商品库存列表
     *
     * @param skuId sku id, 没有可以为null
     * @return 商品库存列表
     */
    @GetMapping("ware/waresku/list/{skuId}")
    Result<PageResult<WareSku>> listBySkuId(@PathVariable("skuId") Long skuId);

    /**
     * 获取商品库存详情
     *
     * @param id 商品库存主键id
     * @return 商品库存详细信息
     */
    @GetMapping("ware/waresku/info/{id}")
    Result<WareSku> info(@PathVariable("id") Long id);

    /**
     * 新增商品库存
     *
     * @param wareSku 商品库存元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("ware/waresku/save")
    Result<Boolean> save(@RequestBody WareSku wareSku);

    /**
     * 修改商品库存
     *
     * @param wareSku 商品库存, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("ware/waresku/update")
    Result<Boolean> update(@RequestBody WareSku wareSku);

    /**
     * 批量删除商品库存
     *
     * @param ids 商品库存id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("ware/waresku/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
