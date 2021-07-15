package cn.alphahub.mall.ware.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品库存-feign远程调用顶层api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RequestMapping("ware/waresku")
public interface WareSkuApi {
    /**
     * 下单锁定库存
     *
     * @param skuLockVo 锁定库存
     * @return 库存锁定结果
     */
    @PostMapping("/order/lock/stock")
    BaseResult<LockStockResultTo> orderLockStock(@RequestBody WareSkuLockVo skuLockVo);

    /**
     * 查看是否有库存
     *
     * @param skuIds sku id 集合
     * @return 商品库存列表
     */
    @PostMapping("skuHasStock")
    BaseResult<List<WareSkuVO>> getSkuHasStock(@RequestBody List<Long> skuIds);

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
    @GetMapping("/list")
    BaseResult<PageResult<WareSku>> list(
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
    @GetMapping("/list/{skuId}")
    BaseResult<PageResult<WareSku>> listBySkuId(@PathVariable("skuId") Long skuId);

    /**
     * 获取商品库存详情
     *
     * @param id 商品库存主键id
     * @return 商品库存详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<WareSku> info(@PathVariable("id") Long id);

    /**
     * 新增商品库存
     *
     * @param wareSku 商品库存元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody WareSku wareSku);

    /**
     * 修改商品库存
     *
     * @param wareSku 商品库存, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody WareSku wareSku);

    /**
     * 批量删除商品库存
     *
     * @param ids 商品库存id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
