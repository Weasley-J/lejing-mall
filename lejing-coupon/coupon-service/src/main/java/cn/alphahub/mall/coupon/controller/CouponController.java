package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.entity.CouponEntity;
import cn.alphahub.mall.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 优惠券信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:22:38
 */
@RestController
@RequestMapping("coupon/coupon")
public class CouponController extends BaseController {
    @Autowired
    private CouponService couponService;

    /**
     * 查询优惠券信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param couponEntity 优惠券信息,按需传入,默认等值查询
     * @return 优惠券信息分页列表
     */
    @GetMapping("/list")
    public BaseResult<PageResult<CouponEntity>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponEntity couponEntity
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponEntity> pageResult = couponService.queryPage(pageDomain, couponEntity);
        return (BaseResult<PageResult<CouponEntity>>) toPageableResult(pageResult);
    }

    /**
     * 获取优惠券信息详情
     *
     * @param id 主键id
     * @return 优惠券信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<CouponEntity> info(@PathVariable("id") Long id) {
        CouponEntity coupon = couponService.getById(id);
        return (BaseResult<CouponEntity>) toResponseResult(coupon);
    }

    /**
     * 保存优惠券信息
     *
     * @param coupon 优惠券信息元数据
     * @return 操作提示
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(CouponEntity coupon) {
        BaseResult<Boolean> r = new BaseResult<>();
        boolean save = couponService.save(coupon);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券信息
     *
     * @param coupon 优惠券信息，根据主键id按需更新
     * @return 操作提示
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(CouponEntity coupon) {
        boolean update = couponService.updateById(coupon);
        return toOperationResult(update);
    }

    /**
     * 根据id集合批量删除优惠券信息
     *
     * @param ids 优惠券信息id集合
     * @return 操作提示
     */
    @DeleteMapping("/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
