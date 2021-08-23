package cn.alphahub.mall.sys.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysDictType;
import cn.alphahub.mall.sys.service.SysDictTypeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 字典类型Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:08:07
 */
@RestController
@RequestMapping("sys/sysdicttype")
public class SysDictTypeController extends BaseController {
    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典类型列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysDictType 字典类型, 查询字段选择性传入, 默认为等值查询
     * @return 字典类型分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysDictType>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysDictType sysDictType
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysDictType> pageResult = sysDictTypeService.queryPage(pageDomain, sysDictType);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典类型主键id
     * @return 字典类型详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SysDictType> info(@PathVariable("id") Integer id) {
        SysDictType sysDictType = sysDictTypeService.getById(id);
        return ObjectUtils.anyNotNull(sysDictType) ? BaseResult.ok(sysDictType) : BaseResult.fail();
    }

    /**
     * 新增字典类型
     *
     * @param sysDictType 字典类型元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SysDictType sysDictType) {
        boolean save = sysDictTypeService.save(sysDictType);
        return toOperationResult(save);
    }

    /**
     * 修改字典类型
     *
     * @param sysDictType 字典类型, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SysDictType sysDictType) {
        boolean update = sysDictTypeService.updateById(sysDictType);
        return toOperationResult(update);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 字典类型id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Integer[] ids) {
        boolean delete = sysDictTypeService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
