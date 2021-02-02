package cn.alphahub.mall.order.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.order.entity.RefundInfoEntity;
import cn.alphahub.mall.order.service.RefundInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 退款信息
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
@RestController
@RequestMapping("order/refundinfo")
public class RefundInfoController {
    @Autowired
    private RefundInfoService refundInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("order:refundinfo:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = refundInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("order:refundinfo:info")
    public R info(@PathVariable("id") Long id) {
        RefundInfoEntity refundInfo = refundInfoService.getById(id);

        return R.ok().put("refundInfo", refundInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("order:refundinfo:save")
    public R save(@RequestBody RefundInfoEntity refundInfo) {
        refundInfoService.save(refundInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("order:refundinfo:update")
    public R update(@RequestBody RefundInfoEntity refundInfo) {
        refundInfoService.updateById(refundInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("order:refundinfo:delete")
    public R delete(@RequestBody Long[] ids) {
        refundInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
