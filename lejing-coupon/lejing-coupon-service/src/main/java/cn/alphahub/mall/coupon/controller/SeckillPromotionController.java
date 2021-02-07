package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillPromotion;
import cn.alphahub.mall.coupon.service.SeckillPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 秒杀活动Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/seckillpromotion")
public class SeckillPromotionController extends BaseController {
    @Autowired
    private SeckillPromotionService seckillPromotionService;

    /**
     * 查询秒杀活动列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param seckillPromotion 秒杀活动,字段选择性传入,默认为等值查询
     * @return 秒杀活动分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillpromotion:list")
    public BaseResult<PageResult<SeckillPromotion>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillPromotion seckillPromotion
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillPromotion> pageResult = seckillPromotionService.queryPage(pageDomain, seckillPromotion);
        return (BaseResult<PageResult<SeckillPromotion>>) toPageableResult(pageResult);
    }

    /**
     * 获取秒杀活动详情
     *
     * @param id 秒杀活动主键id
     * @return 秒杀活动详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillpromotion:info")
    public BaseResult<SeckillPromotion> info(@PathVariable("id") Long id) {
        SeckillPromotion seckillPromotion = seckillPromotionService.getById(id);
        return (BaseResult<SeckillPromotion>) toResponseResult(seckillPromotion);
    }

    /**
     * 新增秒杀活动
     *
     * @param seckillPromotion 秒杀活动元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:seckillpromotion:save")
    public BaseResult<Boolean> save(@RequestBody SeckillPromotion seckillPromotion) {
        boolean save = seckillPromotionService.save(seckillPromotion);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀活动
     *
     * @param seckillPromotion 秒杀活动,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:seckillpromotion:update")
    public BaseResult<Boolean> update(@RequestBody SeckillPromotion seckillPromotion) {
        boolean update = seckillPromotionService.updateById(seckillPromotion);
        return toOperationResult(update);
    }

    /**
     * 批量删除秒杀活动
     *
     * @param ids 秒杀活动id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:seckillpromotion:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = seckillPromotionService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
