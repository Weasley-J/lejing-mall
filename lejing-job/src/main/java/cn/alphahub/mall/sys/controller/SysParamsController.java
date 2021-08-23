package cn.alphahub.mall.sys.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.sys.domain.SysParams;
import cn.alphahub.mall.sys.service.SysParamsService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 参数管理Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-24 00:08:07
 */
@RestController
@RequestMapping("sys/sysparams")
public class SysParamsController extends BaseController {
    @Resource
    private SysParamsService sysParamsService;

    /**
     * 查询参数管理列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysParams   参数管理, 查询字段选择性传入, 默认为等值查询
     * @return 参数管理分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysParams>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysParams sysParams
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysParams> pageResult = sysParamsService.queryPage(pageDomain, sysParams);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取参数管理详情
     *
     * @param id 参数管理主键id
     * @return 参数管理详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SysParams> info(@PathVariable("id") Long id) {
        SysParams sysParams = sysParamsService.getById(id);
        return ObjectUtils.anyNotNull(sysParams) ? BaseResult.ok(sysParams) : BaseResult.fail();
    }

    /**
     * 新增参数管理
     *
     * @param sysParams 参数管理元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SysParams sysParams) {
        boolean save = sysParamsService.save(sysParams);
        return toOperationResult(save);
    }

    /**
     * 修改参数管理
     *
     * @param sysParams 参数管理, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SysParams sysParams) {
        boolean update = sysParamsService.updateById(sysParams);
        return toOperationResult(update);
    }

    /**
     * 批量删除参数管理
     *
     * @param ids 参数管理id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = sysParamsService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
