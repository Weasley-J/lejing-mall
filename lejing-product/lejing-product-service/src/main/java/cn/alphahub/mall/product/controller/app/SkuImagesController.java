package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SkuImages;
import cn.alphahub.mall.product.service.SkuImagesService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * sku图片Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/skuimages")
public class SkuImagesController extends BaseController {
    @Resource
    private SkuImagesService skuImagesService;

    /**
     * 查询sku图片列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param skuImages   sku图片,查询字段选择性传入,默认为等值查询
     * @return sku图片分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SkuImages>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SkuImages skuImages
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SkuImages> pageResult = skuImagesService.queryPage(pageDomain, skuImages);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取sku图片详情
     *
     * @param id sku图片主键id
     * @return sku图片详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SkuImages> info(@PathVariable("id") Long id) {
        SkuImages skuImages = skuImagesService.getById(id);
        return ObjectUtils.anyNotNull(skuImages) ? Result.ok(skuImages) : Result.fail();
    }

    /**
     * 新增sku图片
     *
     * @param skuImages sku图片元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SkuImages skuImages) {
        boolean save = skuImagesService.save(skuImages);
        return toOperationResult(save);
    }

    /**
     * 修改sku图片
     *
     * @param skuImages sku图片,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SkuImages skuImages) {
        boolean update = skuImagesService.updateById(skuImages);
        return toOperationResult(update);
    }

    /**
     * 批量删除sku图片
     *
     * @param ids sku图片id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = skuImagesService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
