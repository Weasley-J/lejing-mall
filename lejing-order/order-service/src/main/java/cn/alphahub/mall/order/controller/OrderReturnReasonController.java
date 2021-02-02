package cn.alphahub.mall.order.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.order.entity.OrderReturnReasonEntity;
import cn.alphahub.mall.order.service.OrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 退货原因
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
@RestController
@RequestMapping("order/orderreturnreason")
public class OrderReturnReasonController {
    @Autowired
    private OrderReturnReasonService orderReturnReasonService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:orderreturnreason:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderReturnReasonService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("order:orderreturnreason:info")
    public R info(@PathVariable("id") Long id) {
        OrderReturnReasonEntity orderReturnReason = orderReturnReasonService.getById(id);

        return R.ok().put("orderReturnReason", orderReturnReason);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:orderreturnreason:save")
    public R save(@RequestBody OrderReturnReasonEntity orderReturnReason) {
        orderReturnReasonService.save(orderReturnReason);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:orderreturnreason:update")
    public R update(@RequestBody OrderReturnReasonEntity orderReturnReason) {
        orderReturnReasonService.updateById(orderReturnReason);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("order:orderreturnreason:delete")
    public R delete(@RequestBody Long[] ids) {
        orderReturnReasonService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
