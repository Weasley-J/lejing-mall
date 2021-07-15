package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.CouponSpuRelation;
import cn.alphahub.mall.coupon.service.CouponSpuRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 优惠券与产品关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/couponspurelation")
public class CouponSpuRelationController extends BaseController {
    @Resource
    private CouponSpuRelationService couponSpuRelationService;

    /**
     * 查询优惠券与产品关联列表
     *
     * @param page              当前页码,默认第1页
     * @param rows              显示行数,默认10条
     * @param orderColumn       排序排序字段,默认不排序
     * @param isAsc             排序方式,desc或者asc
     * @param couponSpuRelation 优惠券与产品关联, 查询字段选择性传入, 默认为等值查询
     * @return 优惠券与产品关联分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<CouponSpuRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CouponSpuRelation couponSpuRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CouponSpuRelation> pageResult = couponSpuRelationService.queryPage(pageDomain, couponSpuRelation);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取优惠券与产品关联详情
     *
     * @param id 优惠券与产品关联主键id
     * @return 优惠券与产品关联详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<CouponSpuRelation> info(@PathVariable("id") Long id) {
        CouponSpuRelation couponSpuRelation = couponSpuRelationService.getById(id);
        return ObjectUtils.anyNotNull(couponSpuRelation) ? BaseResult.ok(couponSpuRelation) : BaseResult.fail();
    }

    /**
     * 新增优惠券与产品关联
     *
     * @param couponSpuRelation 优惠券与产品关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody CouponSpuRelation couponSpuRelation) {
        boolean save = couponSpuRelationService.save(couponSpuRelation);
        return toOperationResult(save);
    }

    /**
     * 修改优惠券与产品关联
     *
     * @param couponSpuRelation 优惠券与产品关联, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody CouponSpuRelation couponSpuRelation) {
        boolean update = couponSpuRelationService.updateById(couponSpuRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除优惠券与产品关联
     *
     * @param ids 优惠券与产品关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = couponSpuRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
