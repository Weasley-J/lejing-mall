package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuCategoryRelation;
import cn.alphahub.mall.coupon.service.CouponSpuCategoryRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 优惠券分类关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/couponspucategoryrelation")
public class CouponSpuCategoryRelationController extends BaseController {
    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    /**
     * 查询优惠券分类关联列表
     *
     * @param page                      当前页码,默认第1页
     * @param rows                      显示行数,默认10条
     * @param orderColumn               排序排序字段,默认不排序
     * @param isAsc                     排序方式,desc或者asc
     * @param couponSpuCategoryRelation 优惠券分类关联,字段选择性传入,默认为等值查询
     * @return 优惠券分类关联分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:couponspucategoryrelation:list")
    public BaseResult<PageResult<CouponSpuCategoryRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponSpuCategoryRelation couponSpuCategoryRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponSpuCategoryRelation> pageResult = couponSpuCategoryRelationService.queryPage(pageDomain, couponSpuCategoryRelation);
        return (BaseResult<PageResult<CouponSpuCategoryRelation>>) toPageableResult(pageResult);
    }

    /**
     * 获取优惠券分类关联详情
     *
     * @param id 优惠券分类关联主键id
     * @return 优惠券分类关联详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:couponspucategoryrelation:info")
    public BaseResult<CouponSpuCategoryRelation> info(@PathVariable("id") Long id) {
        CouponSpuCategoryRelation couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);
        return (BaseResult<CouponSpuCategoryRelation>) toResponseResult(couponSpuCategoryRelation);
    }

    /**
     * 新增优惠券分类关联
     *
     * @param couponSpuCategoryRelation 优惠券分类关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:couponspucategoryrelation:save")
    public BaseResult<Boolean> save(@RequestBody CouponSpuCategoryRelation couponSpuCategoryRelation) {
        boolean save = couponSpuCategoryRelationService.save(couponSpuCategoryRelation);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券分类关联
     *
     * @param couponSpuCategoryRelation 优惠券分类关联,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponspucategoryrelation:update")
    public BaseResult<Boolean> update(@RequestBody CouponSpuCategoryRelation couponSpuCategoryRelation) {
        boolean update = couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券分类关联
     *
     * @param ids 优惠券分类关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:couponspucategoryrelation:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
