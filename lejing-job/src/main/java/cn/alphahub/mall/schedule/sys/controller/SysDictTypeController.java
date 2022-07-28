package cn.alphahub.mall.schedule.sys.controller;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.schedule.sys.domain.SysDictType;
import cn.alphahub.mall.schedule.sys.service.SysDictTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 字典类型Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-28 22:03:32
 */
@RestController
@RequestMapping("/schedule/sys/sysdicttype")
public class SysDictTypeController {
    @Resource
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典类型列表
     *
     * @param page        分页参数
     * @param sysDictType 字典类型, 查询字段选择性传入, 默认为等值查询
     * @return 字典类型分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SysDictType>> list(@ModelAttribute(name = "page") PageDomain page,
                                                @ModelAttribute(name = "sysDictType") SysDictType sysDictType
    ) {
        PageResult<SysDictType> pageResult = sysDictTypeService.queryPage(page, sysDictType);
        return Result.ok(pageResult);
    }

    /**
     * 获取字典类型详情
     *
     * @param id 字典类型主键id
     * @return 字典类型详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SysDictType> info(@PathVariable("id") Integer id) {
        SysDictType sysDictType = sysDictTypeService.getById(id);
        return Result.ok(sysDictType);
    }

    /**
     * 新增字典类型
     *
     * @param sysDictType 字典类型元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SysDictType sysDictType) {
        boolean save = sysDictTypeService.save(sysDictType);
        return Result.ok(save);
    }

    /**
     * 修改字典类型
     *
     * @param sysDictType 字典类型, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SysDictType sysDictType) {
        boolean update = sysDictTypeService.updateById(sysDictType);
        return Result.ok(update);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 字典类型id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable(name = "ids") Integer[] ids) {
        boolean delete = sysDictTypeService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
