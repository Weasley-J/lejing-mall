package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.Coupon;
import org.springframework.web.bind.annotation.*;

/**
 * 优惠券信息Controller
 *
 * @author Weasley J
 * @date 2021-02-07 22:41:47
 */
@RequestMapping("coupon/coupon")
public interface CouponApi {

    /**
     * 查询优惠券信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param coupon      优惠券信息,字段选择性传入,默认为等值查询
     * @return 优惠券信息分页数据
     */
    @PostMapping("/list")
    @SuppressWarnings("unchecked")
    BaseResult<PageResult<Coupon>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Coupon coupon
    );

    /**
     * 获取优惠券信息详情
     *
     * @param id 优惠券信息主键id
     * @return 优惠券信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    BaseResult<Coupon> info(@PathVariable("id") Long id);

    /**
     * 新增优惠券信息
     *
     * @param coupon 优惠券信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody Coupon coupon);

    /**
     * 修改优惠券信息
     *
     * @param coupon 优惠券信息,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody Coupon coupon);

    /**
     * 批量删除优惠券信息
     *
     * @param ids 优惠券信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);

}
