package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillPromotion;
import cn.alphahub.mall.coupon.service.SeckillPromotionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 秒杀活动Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/seckillpromotion")
public class SeckillPromotionController extends BaseController {
    @Resource
    private SeckillPromotionService seckillPromotionService;

    /**
     * 查询秒杀活动列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param seckillPromotion 秒杀活动, 查询字段选择性传入, 默认为等值查询
     * @return 秒杀活动分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SeckillPromotion>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillPromotion seckillPromotion
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillPromotion> pageResult = seckillPromotionService.queryPage(pageDomain, seckillPromotion);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取秒杀活动详情
     *
     * @param id 秒杀活动主键id
     * @return 秒杀活动详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SeckillPromotion> info(@PathVariable("id") Long id) {
        SeckillPromotion seckillPromotion = seckillPromotionService.getById(id);
        return ObjectUtils.anyNotNull(seckillPromotion) ? Result.ok(seckillPromotion) : Result.fail();
    }

    /**
     * 新增秒杀活动
     *
     * @param seckillPromotion 秒杀活动元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SeckillPromotion seckillPromotion) {
        boolean save = seckillPromotionService.save(seckillPromotion);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀活动
     *
     * @param seckillPromotion 秒杀活动, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SeckillPromotion seckillPromotion) {
        boolean update = seckillPromotionService.updateById(seckillPromotion);
        return toOperationResult(update);
    }

    /**
     * 批量删除秒杀活动
     *
     * @param ids 秒杀活动id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = seckillPromotionService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
