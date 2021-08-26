package cn.alphahub.mall.schedule.sys.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.schedule.sys.domain.SysParams;
import cn.alphahub.mall.schedule.sys.service.SysParamsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 参数管理Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-08-25 23:21:20
 */
@RestController
@RequestMapping("/schedule/sys/sysparams")
public class SysParamsController {
    @Resource
    private SysParamsService sysParamsService;

    /**
     * 查询参数管理列表
     *
     * @param page      分页参数
     * @param sysParams 参数管理, 查询字段选择性传入, 默认为等值查询
     * @return 参数管理分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SysParams>> list(@ModelAttribute(name = "page") PageDomain page,
                                                  @ModelAttribute(name = "sysParams") SysParams sysParams
    ) {
        PageResult<SysParams> pageResult = sysParamsService.queryPage(page, sysParams);
        return BaseResult.ok(pageResult);
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
        return BaseResult.ok(sysParams);
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
        return BaseResult.ok(save);
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
        return BaseResult.ok(update);
    }

    /**
     * 批量删除参数管理
     *
     * @param ids 参数管理id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable(name = "ids") Long[] ids) {
        boolean delete = sysParamsService.removeByIds(Arrays.asList(ids));
        return BaseResult.ok(delete);
    }
}
