package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.ProductAttrValue;
import cn.alphahub.mall.product.service.ProductAttrValueService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * spu属性值Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/productattrvalue")
public class ProductAttrValueController {
    @Resource
    private ProductAttrValueService productAttrValueService;

    /**
     * 查询spu属性值列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param productAttrValue spu属性值,查询字段选择性传入,默认为等值查询
     * @return spu属性值分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<ProductAttrValue>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            ProductAttrValue productAttrValue
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<ProductAttrValue> pageResult = productAttrValueService.queryPage(pageDomain, productAttrValue);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取spu属性值详情
     *
     * @param id spu属性值主键id
     * @return spu属性值详细信息
     */
    @GetMapping("/info/{id}")
    public Result<ProductAttrValue> info(@PathVariable("id") Long id) {
        ProductAttrValue productAttrValue = productAttrValueService.getById(id);
        return ObjectUtils.anyNotNull(productAttrValue) ? Result.ok(productAttrValue) : Result.fail();
    }

    /**
     * 新增spu属性值
     *
     * @param productAttrValue spu属性值元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody ProductAttrValue productAttrValue) {
        boolean save = productAttrValueService.save(productAttrValue);
        return Result.ok(save);
    }

    /**
     * 修改spu属性值
     *
     * @param productAttrValue spu属性值,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody ProductAttrValue productAttrValue) {
        boolean update = productAttrValueService.updateById(productAttrValue);
        return Result.ok(update);
    }

    /**
     * 批量删除spu属性值
     *
     * @param ids spu属性值id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = productAttrValueService.removeByIds(Arrays.asList(ids));
        return Result.ok(delete);
    }
}
