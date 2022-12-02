package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import cn.alphahub.mall.coupon.service.SpuBoundsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 商品spu积分设置Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/spubounds")
public class SpuBoundsController {
    @Resource
    private SpuBoundsService spuBoundsService;

    /**
     * 查询商品spu积分设置列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuBounds   商品spu积分设置, 查询字段选择性传入, 默认为等值查询
     * @return 商品spu积分设置分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SpuBounds>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuBounds spuBounds
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuBounds> pageResult = spuBoundsService.queryPage(pageDomain, spuBounds);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品spu积分设置详情
     *
     * @param id 商品spu积分设置主键id
     * @return 商品spu积分设置详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SpuBounds> info(@PathVariable("id") Long id) {
        SpuBounds spuBounds = spuBoundsService.getById(id);
        return ObjectUtils.anyNotNull(spuBounds) ? Result.of(spuBounds) : Result.fail();
    }

    /**
     * 新增商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    @Transactional(rollbackFor = {Exception.class})
    public Result<Boolean> save(@RequestBody SpuBounds spuBounds) {
        boolean save = spuBoundsService.save(spuBounds);
        return Result.of(save);
    }

    /**
     * 修改商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SpuBounds spuBounds) {
        boolean update = spuBoundsService.updateById(spuBounds);
        return Result.of(update);
    }

    /**
     * 批量删除商品spu积分设置
     *
     * @param ids 商品spu积分设置id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = spuBoundsService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
