package cn.alphahub.mall.product.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.vo.AttrRespVO;
import cn.alphahub.mall.product.vo.AttrVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品属性-远程调用api
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021年3月14日
 */
@RequestMapping("product/attr")
public interface AttrApi {

    /**
     * 获取spu规格
     *
     * @param spuId spu Id
     * @return spu规格列表
     */
    @GetMapping("/base/listforspu/{spuId}")
    BaseResult<List<ProductAttrValue>> listSpuBySpuId(@PathVariable("spuId") Long spuId);

    /**
     * 查询属性base/sale list
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param key         搜索关键字
     * @param catelogId   三级分类id
     * @param attrType    属性类型
     * @return 分页列表
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    BaseResult<PageResult<AttrRespVO>> baseList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "key", defaultValue = "") String key,
            @PathVariable(value = "catelogId") Long catelogId,
            @PathVariable(value = "attrType") String attrType
    );

    /**
     * 查询商品属性列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param attr        商品属性,查询字段选择性传入,默认为等值查询
     * @return 商品属性分页数据
     */
    @GetMapping("/list")
    BaseResult<PageResult<Attr>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Attr attr
    );

    /**
     * 获取商品属性详情
     *
     * @param attrId 商品属性主键id
     * @return 商品属性详细信息
     */
    @GetMapping("/info/{attrId}")
    BaseResult<AttrRespVO> info(@PathVariable("attrId") Long attrId);

    /**
     * 新增商品属性
     *
     * @param attr 商品属性元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody AttrVO attr);

    /**
     * 修改商品属性
     *
     * @param attr 商品属性,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody AttrVO attr);

    /**
     * 根据spuId修改商品属性
     *
     * @param spuId      商品spuId
     * @param attrValues spu属性值列表
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update/{spuId}")
    BaseResult<Boolean> updateSpuAttr(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValue> attrValues);

    /**
     * 批量删除商品属性
     *
     * @param attrIds 商品属性id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{attrIds}")
    BaseResult<Boolean> delete(@PathVariable Long[] attrIds);
}
