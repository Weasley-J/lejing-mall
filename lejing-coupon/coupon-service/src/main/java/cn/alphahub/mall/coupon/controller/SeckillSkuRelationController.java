package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.coupon.service.SeckillSkuRelationService;

import java.util.Arrays;

/**
 * 秒杀活动商品关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@RestController
@RequestMapping("coupon/seckillskurelation")
public class SeckillSkuRelationController extends BaseController {
    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    /**
     * 查询秒杀活动商品关联列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param seckillSkuRelation 秒杀活动商品关联,字段选择性传入,默认等值查询
     * @return 秒杀活动商品关联分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillskurelation:list")
    public BaseResult<PageResult<SeckillSkuRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSkuRelation seckillSkuRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillSkuRelation> pageResult = seckillSkuRelationService.queryPage(pageDomain, seckillSkuRelation);
        return (BaseResult<PageResult<SeckillSkuRelation>>) toPageableResult(pageResult);
    }

    /**
     * 获取秒杀活动商品关联详情
     *
     * @param id 秒杀活动商品关联主键id
     * @return 秒杀活动商品关联详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillskurelation:info")
    public BaseResult<SeckillSkuRelation> info(@PathVariable("id") Long id){
        SeckillSkuRelation seckillSkuRelation = seckillSkuRelationService.getById(id);
        return (BaseResult<SeckillSkuRelation>) toResponseResult(seckillSkuRelation);
    }

    /**
     * 新增秒杀活动商品关联
     *
     * @param seckillSkuRelation 秒杀活动商品关联元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:seckillskurelation:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ SeckillSkuRelation seckillSkuRelation) {
        boolean save = seckillSkuRelationService.save(seckillSkuRelation);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀活动商品关联
     *
     * @param seckillSkuRelation 秒杀活动商品关联,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ SeckillSkuRelation seckillSkuRelation) {
        boolean update = seckillSkuRelationService.updateById(seckillSkuRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除秒杀活动商品关联
     *
     * @param ids 秒杀活动商品关联id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:seckillskurelation:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = seckillSkuRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
