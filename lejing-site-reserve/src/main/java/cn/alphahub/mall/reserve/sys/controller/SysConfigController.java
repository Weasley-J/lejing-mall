package cn.alphahub.mall.reserve.sys.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.sys.domain.SysConfig;
import cn.alphahub.mall.reserve.sys.service.SysConfigService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 参数配置表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:19:03
 */
@RestController
@RequestMapping("site/sys/sysconfig")
public class SysConfigController extends BaseController {
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 查询参数配置表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param sysConfig   参数配置表, 查询字段选择性传入, 默认为等值查询
     * @return 参数配置表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysConfig>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SysConfig sysConfig
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SysConfig> pageResult = sysConfigService.queryPage(pageDomain, sysConfig);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取参数配置表详情
     *
     * @param configId 参数配置表主键id
     * @return 参数配置表详细信息
     */
    @GetMapping("/info/{configId}")
    public BaseResult<SysConfig> info(@PathVariable("configId") Long configId) {
        SysConfig sysConfig = sysConfigService.getById(configId);
        return ObjectUtils.anyNotNull(sysConfig) ? BaseResult.ok(sysConfig) : BaseResult.fail();
    }

    /**
     * 新增参数配置表
     *
     * @param sysConfig 参数配置表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SysConfig sysConfig) {
        boolean save = sysConfigService.save(sysConfig);
        return toOperationResult(save);
    }

    /**
     * 修改参数配置表
     *
     * @param sysConfig 参数配置表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SysConfig sysConfig) {
        boolean update = sysConfigService.updateById(sysConfig);
        return toOperationResult(update);
    }

    /**
     * 批量删除参数配置表
     *
     * @param configIds 参数配置表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{configIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] configIds) {
        boolean delete = sysConfigService.removeByIds(Arrays.asList(configIds));
        return toOperationResult(delete);
    }
}
