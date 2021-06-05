package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import org.springframework.web.bind.annotation.*;

/**
 * 商品spu积分设置feign远程调用接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-14 18:57:50
 */
@RequestMapping("coupon/spubounds")
public interface SpuBoundsApi {
    /**
     * 查询商品spu积分设置列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuBounds   商品spu积分设置,查询字段选择性传入,默认为等值查询
     * @return 商品spu积分设置分页数据
     */
    @GetMapping("/list")
    BaseResult<PageResult<SpuBounds>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuBounds spuBounds
    );

    /**
     * 获取商品spu积分设置详情
     *
     * @param id 商品spu积分设置主键id
     * @return 商品spu积分设置详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<SpuBounds> info(@PathVariable("id") Long id);

    /**
     * 新增商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody SpuBounds spuBounds);

    /**
     * 修改商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody SpuBounds spuBounds);

    /**
     * 批量删除商品spu积分设置
     *
     * @param ids 商品spu积分设置id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
