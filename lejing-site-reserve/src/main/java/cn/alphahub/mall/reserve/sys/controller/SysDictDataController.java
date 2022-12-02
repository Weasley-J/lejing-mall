package cn.alphahub.mall.reserve.sys.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.sys.domain.SysDictData;
import cn.alphahub.mall.reserve.sys.service.SysDictDataService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 字典数据表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:19:03
 */
@RestController
@RequestMapping("site/sys/sysdictdata")
public class SysDictDataController {
    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 查询字典数据表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysDictData 字典数据表, 查询字段选择性传入, 默认为等值查询
     * @return 字典数据表分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SysDictData>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysDictData sysDictData
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysDictData> pageResult = sysDictDataService.queryPage(pageDomain, sysDictData);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取字典数据表详情
     *
     * @param dictCode 字典数据表主键id
     * @return 字典数据表详细信息
     */
    @GetMapping("/info/{dictCode}")
    public Result<SysDictData> info(@PathVariable("dictCode") Long dictCode) {
        SysDictData sysDictData = sysDictDataService.getById(dictCode);
        return ObjectUtils.anyNotNull(sysDictData) ? Result.of(sysDictData) : Result.fail();
    }

    /**
     * 新增字典数据表
     *
     * @param sysDictData 字典数据表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SysDictData sysDictData) {
        boolean save = sysDictDataService.save(sysDictData);
        return Result.of(save);
    }

    /**
     * 修改字典数据表
     *
     * @param sysDictData 字典数据表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SysDictData sysDictData) {
        boolean update = sysDictDataService.updateById(sysDictData);
        return Result.of(update);
    }

    /**
     * 批量删除字典数据表
     *
     * @param dictCodes 字典数据表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{dictCodes}")
    public Result<Boolean> delete(@PathVariable Long[] dictCodes) {
        boolean delete = sysDictDataService.removeByIds(Arrays.asList(dictCodes));
        return Result.of(delete);
    }
}
