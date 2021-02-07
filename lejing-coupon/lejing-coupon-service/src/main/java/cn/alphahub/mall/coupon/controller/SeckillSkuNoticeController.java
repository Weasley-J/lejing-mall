package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuNotice;
import cn.alphahub.mall.coupon.service.SeckillSkuNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 秒杀商品通知订阅Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/seckillskunotice")
public class SeckillSkuNoticeController extends BaseController {
    @Autowired
    private SeckillSkuNoticeService seckillSkuNoticeService;

    /**
     * 查询秒杀商品通知订阅列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param seckillSkuNotice 秒杀商品通知订阅,字段选择性传入,默认为等值查询
     * @return 秒杀商品通知订阅分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillskunotice:list")
    public BaseResult<PageResult<SeckillSkuNotice>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SeckillSkuNotice seckillSkuNotice
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SeckillSkuNotice> pageResult = seckillSkuNoticeService.queryPage(pageDomain, seckillSkuNotice);
        return (BaseResult<PageResult<SeckillSkuNotice>>) toPageableResult(pageResult);
    }

    /**
     * 获取秒杀商品通知订阅详情
     *
     * @param id 秒杀商品通知订阅主键id
     * @return 秒杀商品通知订阅详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:seckillskunotice:info")
    public BaseResult<SeckillSkuNotice> info(@PathVariable("id") Long id) {
        SeckillSkuNotice seckillSkuNotice = seckillSkuNoticeService.getById(id);
        return (BaseResult<SeckillSkuNotice>) toResponseResult(seckillSkuNotice);
    }

    /**
     * 新增秒杀商品通知订阅
     *
     * @param seckillSkuNotice 秒杀商品通知订阅元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:seckillskunotice:save")
    public BaseResult<Boolean> save(@RequestBody SeckillSkuNotice seckillSkuNotice) {
        boolean save = seckillSkuNoticeService.save(seckillSkuNotice);
        return toOperationResult(save);
    }

    /**
     * 修改秒杀商品通知订阅
     *
     * @param seckillSkuNotice 秒杀商品通知订阅,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:seckillskunotice:update")
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
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:seckillskunotice:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = seckillSkuNoticeService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
