package cn.alphahub.mall.product.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.product.vo.SpuSaveVO;
import org.springframework.web.bind.annotation.*;

/**
 * spu信息 - feign api
 *
 * @author Weasley J
 */
public interface SpuInfoApi {
    /**
     * 查询spu信息列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuInfo     spu信息,查询字段选择性传入,默认为等值查询
     * @param key         检索关键字
     * @param sidx        排序字段
     * @param order       排序方式:asc/desc
     * @param catelogId   三级分类id
     * @param brandId     品牌id
     * @param status      商品状态
     * @return spu信息列表分页数据
     */
    @GetMapping("product/spuinfo/list")
    BaseResult<PageResult<SpuInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuInfo spuInfo,
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sidx", defaultValue = "") String sidx,
            @RequestParam(value = "order", defaultValue = "") String order,
            @RequestParam(value = "catelogId", defaultValue = "") Integer catelogId,
            @RequestParam(value = "brandId", defaultValue = "") Integer brandId,
            @RequestParam(value = "status", defaultValue = "") Integer status
    );

    /**
     * 获取spu信息详情
     *
     * @param id spu信息主键id
     * @return spu信息详细信息
     */
    @GetMapping("product/spuinfo/info/{id}")
    BaseResult<SpuInfo> info(@PathVariable("id") Long id);

    /**
     * 新增spu信息
     *
     * @param spuSaveVO spu信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("product/spuinfo/save")
    BaseResult<Boolean> save(@RequestBody SpuSaveVO spuSaveVO);

    /**
     * 上架商品
     *
     * @param spuId 商品spu id
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("product/spuinfo/{spuId}/up")
    BaseResult<Boolean> spuOnShelves(@PathVariable("spuId") Long spuId);

    /**
     * 修改spu信息
     *
     * @param spuInfo spu信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("product/spuinfo/update")
    BaseResult<Boolean> update(@RequestBody SpuInfo spuInfo);

    /**
     * 批量删除spu信息
     *
     * @param ids spu信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("product/spuinfo/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
