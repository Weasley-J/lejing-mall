package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import cn.alphahub.mall.coupon.service.CouponHistoryService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 优惠券领取历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/couponhistory")
public class CouponHistoryController extends BaseController {
    @Resource
    private CouponHistoryService couponHistoryService;

    /**
     * 查询优惠券领取历史记录列表
     *
     * @param page          当前页码,默认第1页
     * @param rows          显示行数,默认10条
     * @param orderColumn   排序排序字段,默认不排序
     * @param isAsc         排序方式,desc或者asc
     * @param couponHistory 优惠券领取历史记录, 查询字段选择性传入, 默认为等值查询
     * @return 优惠券领取历史记录分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<CouponHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponHistory couponHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponHistory> pageResult = couponHistoryService.queryPage(pageDomain, couponHistory);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取优惠券领取历史记录详情
     *
     * @param id 优惠券领取历史记录主键id
     * @return 优惠券领取历史记录详细信息
     */
    @GetMapping("/info/{id}")
    public Result<CouponHistory> info(@PathVariable("id") Long id) {
        CouponHistory couponHistory = couponHistoryService.getById(id);
        return ObjectUtils.anyNotNull(couponHistory) ? Result.ok(couponHistory) : Result.fail();
    }

    /**
     * 新增优惠券领取历史记录
     *
     * @param couponHistory 优惠券领取历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody CouponHistory couponHistory) {
        boolean save = couponHistoryService.save(couponHistory);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券领取历史记录
     *
     * @param couponHistory 优惠券领取历史记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody CouponHistory couponHistory) {
        boolean update = couponHistoryService.updateById(couponHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券领取历史记录
     *
     * @param ids 优惠券领取历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
