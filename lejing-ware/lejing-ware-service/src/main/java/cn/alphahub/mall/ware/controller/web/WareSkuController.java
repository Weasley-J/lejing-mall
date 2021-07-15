package cn.alphahub.mall.ware.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enums.BusinessCodeEnum;
import cn.alphahub.common.exception.NoStockException;
import cn.alphahub.common.to.LockStockResultTo;
import cn.alphahub.mall.order.dto.vo.WareSkuLockVo;
import cn.alphahub.mall.ware.domain.WareSku;
import cn.alphahub.mall.ware.service.WareSkuService;
import cn.alphahub.mall.ware.vo.WareSkuVO;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 商品库存Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController extends BaseController {
    @Resource
    private WareSkuService wareSkuService;

    /**
     * 下单锁定库存
     *
     * @param skuLockVo 锁定库存
     * @return 库存锁定结果
     */
    @PostMapping("/order/lock/stock")
    public BaseResult<LockStockResultTo> orderLockStock(@RequestBody WareSkuLockVo skuLockVo) {
        try {
            LockStockResultTo lockStockResults = wareSkuService.orderLockStock(skuLockVo);
            return BaseResult.ok(lockStockResults);
        } catch (NoStockException e) {
            return BaseResult.error(BusinessCodeEnum.NO_STOCK_EXCEPTION.getCode(), e.getMessage());
        }
    }

    /**
     * 查看是否有库存
     *
     * @param skuIds sku id 集合
     * @return 商品库存列表
     */
    @PostMapping("skuHasStock")
    BaseResult<List<WareSkuVO>> getSkuHasStock(@RequestBody List<Long> skuIds) {
        List<WareSkuVO> vos = wareSkuService.getSkuHasStock(skuIds);
        return CollectionUtils.isNotEmpty(vos) ? BaseResult.success(vos) : BaseResult.error();
    }

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
    public BaseResult<PageResult<WareSku>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareSku wareSku
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<WareSku> pageResult = wareSkuService.queryPage(pageDomain, wareSku);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 查询商品库存列表
     *
     * @param skuId sku id, 没有可以为null
     * @return 商品库存列表
     */
    @GetMapping("/list/{skuId}")
    public BaseResult<PageResult<WareSku>> listBySkuId(@PathVariable("skuId") Long skuId) {
        PageDomain pageDomain = new PageDomain(1, 10, null, null);
        PageResult<WareSku> pageResult = wareSkuService.queryPage(pageDomain, WareSku.builder().skuId(skuId).build());
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品库存详情
     *
     * @param id 商品库存主键id
     * @return 商品库存详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<WareSku> info(@PathVariable("id") Long id) {
        WareSku wareSku = wareSkuService.getById(id);
        return ObjectUtils.anyNotNull(wareSku) ? BaseResult.ok(wareSku) : BaseResult.fail();
    }

    /**
     * 新增商品库存
     *
     * @param wareSku 商品库存元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody WareSku wareSku) {
        boolean save = wareSkuService.save(wareSku);
        return toOperationResult(save);
    }

    /**
     * 修改商品库存
     *
     * @param wareSku 商品库存, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody WareSku wareSku) {
        boolean update = wareSkuService.updateById(wareSku);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品库存
     *
     * @param ids 商品库存id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = wareSkuService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
