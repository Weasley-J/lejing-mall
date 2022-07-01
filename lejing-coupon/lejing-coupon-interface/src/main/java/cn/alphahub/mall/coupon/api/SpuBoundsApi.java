package cn.alphahub.mall.coupon.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品spu积分设置feign远程调用接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-14 18:57:50
 */
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
    @GetMapping("coupon/spubounds/list")
    Result<PageResult<SpuBounds>> list(
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
    @GetMapping("coupon/spubounds/info/{id}")
    Result<SpuBounds> info(@PathVariable("id") Long id);

    /**
     * 新增商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("coupon/spubounds/save")
    Result<Boolean> save(@RequestBody SpuBounds spuBounds);

    /**
     * 修改商品spu积分设置
     *
     * @param spuBounds 商品spu积分设置,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("coupon/spubounds/update")
    Result<Boolean> update(@RequestBody SpuBounds spuBounds);

    /**
     * 批量删除商品spu积分设置
     *
     * @param ids 商品spu积分设置id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("coupon/spubounds/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
