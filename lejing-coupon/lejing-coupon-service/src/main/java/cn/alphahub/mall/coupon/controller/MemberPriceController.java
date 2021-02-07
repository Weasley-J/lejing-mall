package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.MemberPrice;
import cn.alphahub.mall.coupon.service.MemberPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 商品会员价格Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/memberprice")
public class MemberPriceController extends BaseController {
    @Autowired
    private MemberPriceService memberPriceService;

    /**
     * 查询商品会员价格列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param memberPrice 商品会员价格,字段选择性传入,默认为等值查询
     * @return 商品会员价格分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:memberprice:list")
    public BaseResult<PageResult<MemberPrice>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberPrice memberPrice
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberPrice> pageResult = memberPriceService.queryPage(pageDomain, memberPrice);
        return (BaseResult<PageResult<MemberPrice>>) toPageableResult(pageResult);
    }

    /**
     * 获取商品会员价格详情
     *
     * @param id 商品会员价格主键id
     * @return 商品会员价格详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:memberprice:info")
    public BaseResult<MemberPrice> info(@PathVariable("id") Long id) {
        MemberPrice memberPrice = memberPriceService.getById(id);
        return (BaseResult<MemberPrice>) toResponseResult(memberPrice);
    }

    /**
     * 新增商品会员价格
     *
     * @param memberPrice 商品会员价格元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:memberprice:save")
    public BaseResult<Boolean> save(@RequestBody MemberPrice memberPrice) {
        boolean save = memberPriceService.save(memberPrice);
        return toOperationResult(save);
    }

    /**
     * 修改商品会员价格
     *
     * @param memberPrice 商品会员价格,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:memberprice:update")
    public BaseResult<Boolean> update(@RequestBody MemberPrice memberPrice) {
        boolean update = memberPriceService.updateById(memberPrice);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品会员价格
     *
     * @param ids 商品会员价格id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:memberprice:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = memberPriceService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
