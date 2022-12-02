package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SpuInfoDesc;
import cn.alphahub.mall.product.service.SpuInfoDescService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * spu信息介绍Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/spuinfodesc")
public class SpuInfoDescController {
    @Resource
    private SpuInfoDescService spuInfoDescService;

    /**
     * 查询spu信息介绍列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuInfoDesc spu信息介绍,查询字段选择性传入,默认为等值查询
     * @return spu信息介绍分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SpuInfoDesc>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuInfoDesc spuInfoDesc
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuInfoDesc> pageResult = spuInfoDescService.queryPage(pageDomain, spuInfoDesc);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取spu信息介绍详情
     *
     * @param spuId spu信息介绍主键id
     * @return spu信息介绍详细信息
     */
    @GetMapping("/info/{spuId}")
    public Result<SpuInfoDesc> info(@PathVariable("spuId") Long spuId) {
        SpuInfoDesc spuInfoDesc = spuInfoDescService.getById(spuId);
        return ObjectUtils.anyNotNull(spuInfoDesc) ? Result.of(spuInfoDesc) : Result.fail();
    }

    /**
     * 新增spu信息介绍
     *
     * @param spuInfoDesc spu信息介绍元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SpuInfoDesc spuInfoDesc) {
        boolean save = spuInfoDescService.save(spuInfoDesc);
        return Result.of(save);
    }

    /**
     * 修改spu信息介绍
     *
     * @param spuInfoDesc spu信息介绍,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SpuInfoDesc spuInfoDesc) {
        boolean update = spuInfoDescService.updateById(spuInfoDesc);
        return Result.of(update);
    }

    /**
     * 批量删除spu信息介绍
     *
     * @param spuIds spu信息介绍id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{spuIds}")
    public Result<Boolean> delete(@PathVariable Long[] spuIds) {
        boolean delete = spuInfoDescService.removeByIds(Arrays.asList(spuIds));
        return Result.of(delete);
    }
}
