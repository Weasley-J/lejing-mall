package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Attr;
import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.service.AttrService;
import cn.alphahub.mall.product.service.ProductAttrValueService;
import cn.alphahub.mall.product.vo.AttrRespVO;
import cn.alphahub.mall.product.vo.AttrVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 商品属性Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Resource
    private AttrService attrService;
    @Resource
    private ProductAttrValueService productAttrValueService;

    /**
     * 获取spu规格
     *
     * @param spuId spu Id
     * @return spu规格列表
     */
    @GetMapping("/base/listforspu/{spuId}")
    public Result<List<ProductAttrValue>> listSpuBySpuId(@PathVariable("spuId") Long spuId) {
        return Result.ok(productAttrValueService.listSpuBySpuId(spuId));
    }

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
    public Result<PageResult<AttrRespVO>> baseList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "key", defaultValue = "") String key,
            @PathVariable(value = "catelogId") Long catelogId,
            @PathVariable(value = "attrType") String attrType
    ) {
        // attrType=1 -> /base/list/{catelogId} | attrType=2 -> /sale/list/{catelogId}
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<AttrRespVO> pageResult = attrService.queryPage(pageDomain, new Attr(), key, catelogId, attrType);
        return Result.ok(pageResult);
    }

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
    public Result<PageResult<Attr>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Attr attr
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Attr> pageResult = attrService.queryPage(pageDomain, attr);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品属性详情
     *
     * @param attrId 商品属性主键id
     * @return 商品属性详细信息
     */
    @GetMapping("/info/{attrId}")
    @Cacheable(value = "product:attr", key = "'info:'+#root.args[0]")
    public Result<AttrRespVO> info(@PathVariable("attrId") Long attrId) {
        AttrRespVO attr = attrService.getAttrInfoById(attrId);
        return ObjectUtils.anyNotNull(attr) ? Result.ok(attr) : Result.fail();
    }

    /**
     * 新增商品属性
     *
     * @param attr 商品属性元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody AttrVO attr) {
        boolean save = attrService.saveAttr(attr);
        return Result.ok(save);
    }

    /**
     * 修改商品属性
     *
     * @param attr 商品属性,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    @CacheEvict(value = "product:attr", allEntries = true)
    public Result<Boolean> update(@RequestBody AttrVO attr) {
        boolean update = attrService.updateAttrById(attr);
        return Result.ok(update);
    }

    /**
     * 根据spuId修改商品属性
     *
     * @param spuId      商品spuId
     * @param attrValues spu属性值列表
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update/{spuId}")
    @CacheEvict(value = "product:attr", allEntries = true)
    public Result<Boolean> updateSpuAttr(@PathVariable("spuId") Long spuId, @RequestBody List<ProductAttrValue> attrValues) {
        boolean update = productAttrValueService.updateSpuAttr(spuId, attrValues);
        return Result.ok(update);
    }

    /**
     * 批量删除商品属性
     *
     * @param attrIds 商品属性id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{attrIds}")
    public Result<Boolean> delete(@PathVariable Long[] attrIds) {
        boolean delete = attrService.removeByIds(Arrays.asList(attrIds));
        return Result.ok(delete);
    }
}
