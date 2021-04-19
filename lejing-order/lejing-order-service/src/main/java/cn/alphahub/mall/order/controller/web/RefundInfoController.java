package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.RefundInfo;
import cn.alphahub.mall.order.service.RefundInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 退款信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RestController
@RequestMapping("order/refundinfo")
public class RefundInfoController extends BaseController {
    @Resource
    private RefundInfoService refundInfoService;

    /**
     * 查询退款信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param refundInfo  退款信息, 查询字段选择性传入, 默认为等值查询
     * @return 退款信息分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<RefundInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            RefundInfo refundInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<RefundInfo> pageResult = refundInfoService.queryPage(pageDomain, refundInfo);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取退款信息详情
     *
     * @param id 退款信息主键id
     * @return 退款信息详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<RefundInfo> info(@PathVariable("id") Long id) {
        RefundInfo refundInfo = refundInfoService.getById(id);
        return ObjectUtils.anyNotNull(refundInfo) ? BaseResult.ok(refundInfo) : BaseResult.fail();
    }

    /**
     * 新增退款信息
     *
     * @param refundInfo 退款信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody RefundInfo refundInfo) {
        boolean save = refundInfoService.save(refundInfo);
        return toOperationResult(save);
    }

    /**
     * 修改退款信息
     *
     * @param refundInfo 退款信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody RefundInfo refundInfo) {
        boolean update = refundInfoService.updateById(refundInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除退款信息
     *
     * @param ids 退款信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = refundInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
