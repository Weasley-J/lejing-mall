package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuNotice;
import cn.alphahub.mall.coupon.service.SeckillSkuNoticeService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 秒杀商品通知订阅Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/seckillskunotice")
public class SeckillSkuNoticeController extends BaseController {
    @Resource
    private SeckillSkuNoticeService seckillSkuNoticeService;

    /**
     * 查询秒杀商品通知订阅列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param seckillSkuNotice 秒杀商品通知订阅, 查询字段选择性传入, 默认为等值查询
     * @return 秒杀商品通知订阅分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SeckillSkuNotice>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSkuNotice seckillSkuNotice
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillSkuNotice> pageResult = seckillSkuNoticeService.queryPage(pageDomain, seckillSkuNotice);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取秒杀商品通知订阅详情
     *
     * @param id 秒杀商品通知订阅主键id
     * @return 秒杀商品通知订阅详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SeckillSkuNotice> info(@PathVariable("id") Long id) {
        SeckillSkuNotice seckillSkuNotice = seckillSkuNoticeService.getById(id);
        return ObjectUtils.anyNotNull(seckillSkuNotice) ? BaseResult.ok(seckillSkuNotice) : BaseResult.fail();
    }

    /**
     * 新增秒杀商品通知订阅
     *
     * @param seckillSkuNotice 秒杀商品通知订阅元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SeckillSkuNotice seckillSkuNotice) {
        boolean save = seckillSkuNoticeService.save(seckillSkuNotice);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀商品通知订阅
     *
     * @param seckillSkuNotice 秒杀商品通知订阅, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SeckillSkuNotice seckillSkuNotice) {
        boolean update = seckillSkuNoticeService.updateById(seckillSkuNotice);
        return toOperationResult(update);
    }

    /**
     * 批量删除秒杀商品通知订阅
     *
     * @param ids 秒杀商品通知订阅id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = seckillSkuNoticeService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
