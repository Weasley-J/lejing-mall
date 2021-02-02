package cn.alphahub.mall.product.controller;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.common.util.R;
import cn.alphahub.mall.product.entity.SpuImagesEntity;
import cn.alphahub.mall.product.service.SpuImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * spu图片
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:24:22
 */
@RestController
@RequestMapping("product/spuimages")
public class SpuImagesController {
    @Autowired
    private SpuImagesService spuImagesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:spuimages:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = spuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("product:spuimages:info")
    public R info(@PathVariable("id") Long id) {
        SpuImagesEntity spuImages = spuImagesService.getById(id);

        return R.ok().put("spuImages", spuImages);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:spuimages:save")
    public R save(@RequestBody SpuImagesEntity spuImages) {
        spuImagesService.save(spuImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions("product:spuimages:update")
    public R update(@RequestBody SpuImagesEntity spuImages) {
        spuImagesService.updateById(spuImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("product:spuimages:delete")
    public R delete(@RequestBody Long[] ids) {
        spuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
