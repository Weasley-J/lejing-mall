package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SkuInfo;
import cn.alphahub.mall.product.service.SkuInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * sku信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/skuinfo")
public class SkuInfoController {
    @Resource
    private SkuInfoService skuInfoService;

    /**
     * 查询sku信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sidx        排序字段
     * @param order       排序方式:asc/desc
     * @param key         检索关键字
     * @param catelogId   三級分類id
     * @param brandId     品牌id
     * @param min         最低價格
     * @param max         最大價格
     * @return sku信息分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SkuInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "sidx", defaultValue = "") String sidx,
            @RequestParam(value = "order", defaultValue = "") String order,
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "catelogId", defaultValue = "") Long catelogId,
            @RequestParam(value = "brandId", defaultValue = "") Long brandId,
            @RequestParam(value = "min", defaultValue = "") Long min,
            @RequestParam(value = "max", defaultValue = "") Long max
    ) {
        PageDomain pageDomain;
        if (StringUtils.isNoneBlank(sidx, order)) {
            pageDomain = new PageDomain(page, rows, sidx, order);
        } else {
            pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        }
        PageResult<SkuInfo> pageResult = skuInfoService.queryPage(pageDomain, key, catelogId, brandId, min, max);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取sku信息详情
     *
     * @param skuId sku信息主键id
     * @return sku信息详细信息
     */
    @GetMapping("/info/{skuId}")
    public Result<SkuInfo> info(@PathVariable("skuId") Long skuId) {
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        return ObjectUtils.anyNotNull(skuInfo) ? Result.of(skuInfo) : Result.fail();
    }

    /**
     * 新增sku信息
     *
     * @param skuInfo sku信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SkuInfo skuInfo) {
        boolean save = skuInfoService.save(skuInfo);
        return Result.of(save);
    }

    /**
     * 修改sku信息
     *
     * @param skuInfo sku信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SkuInfo skuInfo) {
        boolean update = skuInfoService.updateById(skuInfo);
        return Result.of(update);
    }

    /**
     * 批量删除sku信息
     *
     * @param skuIds sku信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{skuIds}")
    public Result<Boolean> delete(@PathVariable Long[] skuIds) {
        boolean delete = skuInfoService.removeByIds(Arrays.asList(skuIds));
        return Result.of(delete);
    }
}
