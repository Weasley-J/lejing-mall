package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.CouponSpuCategoryRelation;
import cn.alphahub.mall.coupon.service.CouponSpuCategoryRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 优惠券分类关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/couponspucategoryrelation")
public class CouponSpuCategoryRelationController {
    @Resource
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    /**
     * 查询优惠券分类关联列表
     *
     * @param page                      当前页码,默认第1页
     * @param rows                      显示行数,默认10条
     * @param orderColumn               排序排序字段,默认不排序
     * @param isAsc                     排序方式,desc或者asc
     * @param couponSpuCategoryRelation 优惠券分类关联, 查询字段选择性传入, 默认为等值查询
     * @return 优惠券分类关联分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<CouponSpuCategoryRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponSpuCategoryRelation couponSpuCategoryRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponSpuCategoryRelation> pageResult = couponSpuCategoryRelationService.queryPage(pageDomain, couponSpuCategoryRelation);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取优惠券分类关联详情
     *
     * @param id 优惠券分类关联主键id
     * @return 优惠券分类关联详细信息
     */
    @GetMapping("/info/{id}")
    public Result<CouponSpuCategoryRelation> info(@PathVariable("id") Long id) {
        CouponSpuCategoryRelation couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);
        return ObjectUtils.anyNotNull(couponSpuCategoryRelation) ? Result.of(couponSpuCategoryRelation) : Result.fail();
    }

    /**
     * 新增优惠券分类关联
     *
     * @param couponSpuCategoryRelation 优惠券分类关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody CouponSpuCategoryRelation couponSpuCategoryRelation) {
        boolean save = couponSpuCategoryRelationService.save(couponSpuCategoryRelation);
        return Result.of(save);
    }

    /**
     * 修改优惠券分类关联
     *
     * @param couponSpuCategoryRelation 优惠券分类关联, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody CouponSpuCategoryRelation couponSpuCategoryRelation) {
        boolean update = couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);
        return Result.of(update);
    }

    /**
     * 批量删除优惠券分类关联
     *
     * @param ids 优惠券分类关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
