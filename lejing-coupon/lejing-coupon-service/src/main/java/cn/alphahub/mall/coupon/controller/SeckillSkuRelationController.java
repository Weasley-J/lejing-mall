package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.coupon.service.SeckillSkuRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 秒杀活动商品关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/seckillskurelation")
public class SeckillSkuRelationController extends BaseController {
    @Resource
    private SeckillSkuRelationService seckillSkuRelationService;

    /**
     * 查询秒杀活动商品关联列表
     *
     * @param page               当前页码,默认第1页
     * @param rows               显示行数,默认10条
     * @param orderColumn        排序排序字段,默认不排序
     * @param isAsc              排序方式,desc或者asc
     * @param seckillSkuRelation 秒杀活动商品关联, 查询字段选择性传入, 默认为等值查询
     * @return 秒杀活动商品关联分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SeckillSkuRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSkuRelation seckillSkuRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillSkuRelation> pageResult = seckillSkuRelationService.queryPage(pageDomain, seckillSkuRelation);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取秒杀活动商品关联详情
     *
     * @param id 秒杀活动商品关联主键id
     * @return 秒杀活动商品关联详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SeckillSkuRelation> info(@PathVariable("id") Long id) {
        SeckillSkuRelation seckillSkuRelation = seckillSkuRelationService.getById(id);
        return ObjectUtils.anyNotNull(seckillSkuRelation) ? BaseResult.ok(seckillSkuRelation) : BaseResult.fail();
    }

    /**
     * 新增秒杀活动商品关联
     *
     * @param seckillSkuRelation 秒杀活动商品关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SeckillSkuRelation seckillSkuRelation) {
        boolean save = seckillSkuRelationService.save(seckillSkuRelation);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀活动商品关联
     *
     * @param seckillSkuRelation 秒杀活动商品关联, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SeckillSkuRelation seckillSkuRelation) {
        boolean update = seckillSkuRelationService.updateById(seckillSkuRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除秒杀活动商品关联
     *
     * @param ids 秒杀活动商品关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = seckillSkuRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
