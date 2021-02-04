package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.domain.CouponSpuRelation;
import cn.alphahub.mall.coupon.service.CouponSpuRelationService;

import java.util.Arrays;

/**
 * 优惠券与产品关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@RestController
@RequestMapping("coupon/couponspurelation")
public class CouponSpuRelationController extends BaseController {
    @Autowired
    private CouponSpuRelationService couponSpuRelationService;

    /**
     * 查询优惠券与产品关联列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param couponSpuRelation 优惠券与产品关联,字段选择性传入,默认等值查询
     * @return 优惠券与产品关联分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("coupon:couponspurelation:list")
    public BaseResult<PageResult<CouponSpuRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponSpuRelation couponSpuRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponSpuRelation> pageResult = couponSpuRelationService.queryPage(pageDomain, couponSpuRelation);
        return (BaseResult<PageResult<CouponSpuRelation>>) toPageableResult(pageResult);
    }

    /**
     * 获取优惠券与产品关联详情
     *
     * @param id 优惠券与产品关联主键id
     * @return 优惠券与产品关联详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<CouponSpuRelation> info(@PathVariable("id") Long id){
        CouponSpuRelation couponSpuRelation = couponSpuRelationService.getById(id);
        return (BaseResult<CouponSpuRelation>) toResponseResult(couponSpuRelation);
    }

    /**
     * 新增优惠券与产品关联
     *
     * @param couponSpuRelation 优惠券与产品关联元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:couponspurelation:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ CouponSpuRelation couponSpuRelation) {
        boolean save = couponSpuRelationService.save(couponSpuRelation);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券与产品关联
     *
     * @param couponSpuRelation 优惠券与产品关联,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ CouponSpuRelation couponSpuRelation) {
        boolean update = couponSpuRelationService.updateById(couponSpuRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券与产品关联
     *
     * @param ids 优惠券与产品关联id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:couponspurelation:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = couponSpuRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}