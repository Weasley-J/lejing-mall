package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponHistory;
import cn.alphahub.mall.coupon.service.CouponHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 优惠券领取历史记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/couponhistory")
public class CouponHistoryController extends BaseController {
    @Autowired
    private CouponHistoryService couponHistoryService;

    /**
     * 查询优惠券领取历史记录列表
     *
     * @param page          当前页码,默认第1页
     * @param rows          显示行数,默认10条
     * @param orderColumn   排序排序字段,默认不排序
     * @param isAsc         排序方式,desc或者asc
     * @param couponHistory 优惠券领取历史记录,字段选择性传入,默认为等值查询
     * @return 优惠券领取历史记录分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:couponhistory:list")
    public BaseResult<PageResult<CouponHistory>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponHistory couponHistory
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponHistory> pageResult = couponHistoryService.queryPage(pageDomain, couponHistory);
        return (BaseResult<PageResult<CouponHistory>>) toPageableResult(pageResult);
    }

    /**
     * 获取优惠券领取历史记录详情
     *
     * @param id 优惠券领取历史记录主键id
     * @return 优惠券领取历史记录详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:couponhistory:info")
    public BaseResult<CouponHistory> info(@PathVariable("id") Long id) {
        CouponHistory couponHistory = couponHistoryService.getById(id);
        return (BaseResult<CouponHistory>) toResponseResult(couponHistory);
    }

    /**
     * 新增优惠券领取历史记录
     *
     * @param couponHistory 优惠券领取历史记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:couponhistory:save")
    public BaseResult<Boolean> save(@RequestBody CouponHistory couponHistory) {
        boolean save = couponHistoryService.save(couponHistory);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券领取历史记录
     *
     * @param couponHistory 优惠券领取历史记录,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(@RequestBody CouponHistory couponHistory) {
        boolean update = couponHistoryService.updateById(couponHistory);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券领取历史记录
     *
     * @param ids 优惠券领取历史记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:couponhistory:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponHistoryService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
