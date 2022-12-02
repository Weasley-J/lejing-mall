package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.SpuImages;
import cn.alphahub.mall.product.service.SpuImagesService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * spu图片Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/spuimages")
public class SpuImagesController {
    @Resource
    private SpuImagesService spuImagesService;

    /**
     * 查询spu图片列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuImages   spu图片,查询字段选择性传入,默认为等值查询
     * @return spu图片分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<SpuImages>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuImages spuImages
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuImages> pageResult = spuImagesService.queryPage(pageDomain, spuImages);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取spu图片详情
     *
     * @param id spu图片主键id
     * @return spu图片详细信息
     */
    @GetMapping("/info/{id}")
    public Result<SpuImages> info(@PathVariable("id") Long id) {
        SpuImages spuImages = spuImagesService.getById(id);
        return ObjectUtils.anyNotNull(spuImages) ? Result.of(spuImages) : Result.fail();
    }

    /**
     * 新增spu图片
     *
     * @param spuImages spu图片元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody SpuImages spuImages) {
        boolean save = spuImagesService.save(spuImages);
        return Result.of(save);
    }

    /**
     * 修改spu图片
     *
     * @param spuImages spu图片,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody SpuImages spuImages) {
        boolean update = spuImagesService.updateById(spuImages);
        return Result.of(update);
    }

    /**
     * 批量删除spu图片
     *
     * @param ids spu图片id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = spuImagesService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
