package cn.alphahub.mall.ware.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.ware.domain.WareOrderTaskDetail;
import cn.alphahub.mall.ware.service.WareOrderTaskDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 库存工作单Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@RestController
@RequestMapping("ware/wareordertaskdetail")
public class WareOrderTaskDetailController {
    @Resource
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    /**
     * 查询库存工作单列表
     *
     * @param page                当前页码,默认第1页
     * @param rows                显示行数,默认10条
     * @param orderColumn         排序排序字段,默认不排序
     * @param isAsc               排序方式,desc或者asc
     * @param wareOrderTaskDetail 库存工作单, 查询字段选择性传入, 默认为等值查询
     * @return 库存工作单分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<WareOrderTaskDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            WareOrderTaskDetail wareOrderTaskDetail
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<WareOrderTaskDetail> pageResult = wareOrderTaskDetailService.queryPage(pageDomain, wareOrderTaskDetail);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取库存工作单详情
     *
     * @param id 库存工作单主键id
     * @return 库存工作单详细信息
     */
    @GetMapping("/info/{id}")
    public Result<WareOrderTaskDetail> info(@PathVariable("id") Long id) {
        WareOrderTaskDetail wareOrderTaskDetail = wareOrderTaskDetailService.getById(id);
        return ObjectUtils.anyNotNull(wareOrderTaskDetail) ? Result.of(wareOrderTaskDetail) : Result.fail();
    }

    /**
     * 新增库存工作单
     *
     * @param wareOrderTaskDetail 库存工作单元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody WareOrderTaskDetail wareOrderTaskDetail) {
        boolean save = wareOrderTaskDetailService.save(wareOrderTaskDetail);
        return Result.of(save);
    }

    /**
     * 修改库存工作单
     *
     * @param wareOrderTaskDetail 库存工作单, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody WareOrderTaskDetail wareOrderTaskDetail) {
        boolean update = wareOrderTaskDetailService.updateById(wareOrderTaskDetail);
        return Result.of(update);
    }

    /**
     * 批量删除库存工作单
     *
     * @param ids 库存工作单id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = wareOrderTaskDetailService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
