package cn.alphahub.mall.coupon.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SkuLadder;
import cn.alphahub.mall.coupon.service.SkuLadderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 商品阶梯价格Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:41:47
 */
@RestController
@RequestMapping("coupon/skuladder")
public class SkuLadderController extends BaseController {
    @Autowired
    private SkuLadderService skuLadderService;

    /**
     * 查询商品阶梯价格列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param skuLadder   商品阶梯价格,字段选择性传入,默认为等值查询
     * @return 商品阶梯价格分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:skuladder:list")
    public BaseResult<PageResult<SkuLadder>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuLadder skuLadder
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuLadder> pageResult = skuLadderService.queryPage(pageDomain, skuLadder);
        return (BaseResult<PageResult<SkuLadder>>) toPageableResult(pageResult);
    }

    /**
     * 获取商品阶梯价格详情
     *
     * @param id 商品阶梯价格主键id
     * @return 商品阶梯价格详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("coupon:skuladder:info")
    public BaseResult<SkuLadder> info(@PathVariable("id") Long id) {
        SkuLadder skuLadder = skuLadderService.getById(id);
        return (BaseResult<SkuLadder>) toResponseResult(skuLadder);
    }

    /**
     * 新增商品阶梯价格
     *
     * @param skuLadder 商品阶梯价格元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("coupon:skuladder:save")
    public BaseResult<Boolean> save(@RequestBody SkuLadder skuLadder) {
        boolean save = skuLadderService.save(skuLadder);
        return toOperationResult(save);
    }

    /**
     * 修改商品阶梯价格
     *
     * @param skuLadder 商品阶梯价格,根据主键id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:skuladder:update")
    public BaseResult<Boolean> update(@RequestBody SkuLadder skuLadder) {
        boolean update = skuLadderService.updateById(skuLadder);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品阶梯价格
     *
     * @param ids 商品阶梯价格id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("coupon:skuladder:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = skuLadderService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
