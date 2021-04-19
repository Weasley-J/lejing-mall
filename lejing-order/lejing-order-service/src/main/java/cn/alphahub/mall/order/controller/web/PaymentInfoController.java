package cn.alphahub.mall.order.controller.web;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.PaymentInfo;
import cn.alphahub.mall.order.service.PaymentInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 支付信息表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@RestController
@RequestMapping("order/paymentinfo")
public class PaymentInfoController extends BaseController {
    @Resource
    private PaymentInfoService paymentInfoService;

    /**
     * 查询支付信息表列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param paymentInfo 支付信息表, 查询字段选择性传入, 默认为等值查询
     * @return 支付信息表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<PaymentInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            PaymentInfo paymentInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<PaymentInfo> pageResult = paymentInfoService.queryPage(pageDomain, paymentInfo);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取支付信息表详情
     *
     * @param id 支付信息表主键id
     * @return 支付信息表详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<PaymentInfo> info(@PathVariable("id") Long id) {
        PaymentInfo paymentInfo = paymentInfoService.getById(id);
        return ObjectUtils.anyNotNull(paymentInfo) ? BaseResult.ok(paymentInfo) : BaseResult.fail();
    }

    /**
     * 新增支付信息表
     *
     * @param paymentInfo 支付信息表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody PaymentInfo paymentInfo) {
        boolean save = paymentInfoService.save(paymentInfo);
        return toOperationResult(save);
    }

    /**
     * 修改支付信息表
     *
     * @param paymentInfo 支付信息表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody PaymentInfo paymentInfo) {
        boolean update = paymentInfoService.updateById(paymentInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除支付信息表
     *
     * @param ids 支付信息表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = paymentInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
