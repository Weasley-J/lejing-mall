package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.common.utils.R;
import cn.alphahub.mall.coupon.entity.CouponSpuCategoryRelationEntity;
import cn.alphahub.mall.coupon.service.CouponSpuCategoryRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 优惠券分类关联
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:27:55
 */
@RestController
@RequestMapping("coupon/couponspucategoryrelation")
public class CouponSpuCategoryRelationController {
    @Autowired
    private CouponSpuCategoryRelationService couponSpuCategoryRelationService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("coupon:couponspucategoryrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = couponSpuCategoryRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("coupon:couponspucategoryrelation:info")
    public R info(@PathVariable("id") Long id) {
        CouponSpuCategoryRelationEntity couponSpuCategoryRelation = couponSpuCategoryRelationService.getById(id);

        return R.ok().put("couponSpuCategoryRelation", couponSpuCategoryRelation);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:couponspucategoryrelation:save")
    public R save(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
        couponSpuCategoryRelationService.save(couponSpuCategoryRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponspucategoryrelation:update")
    public R update(@RequestBody CouponSpuCategoryRelationEntity couponSpuCategoryRelation) {
        couponSpuCategoryRelationService.updateById(couponSpuCategoryRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("coupon:couponspucategoryrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        couponSpuCategoryRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
