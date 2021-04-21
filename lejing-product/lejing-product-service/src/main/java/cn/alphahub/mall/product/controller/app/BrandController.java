package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.common.valid.group.UpdateGroup;
import cn.alphahub.common.valid.group.UpdateStatusGroup;
import cn.alphahub.mall.product.domain.Brand;
import cn.alphahub.mall.product.service.BrandService;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 品牌Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/brand")
public class BrandController extends BaseController {

    @Resource
    private BrandService brandService;

    /**
     * 查询品牌列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param brand       品牌,查询字段选择性传入,默认为等值查询
     * @param key         查询关键字
     * @return 品牌分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<Brand>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "key", defaultValue = "") String key,
            Brand brand
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Brand> pageResult = brandService.queryPage(pageDomain, brand, key);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取品牌详情
     *
     * @param brandId 品牌主键id
     * @return 品牌详细信息
     */
    @GetMapping("/info/{brandId}")
    @Cacheable(value = "product:brand", key = "'info:'+#root.args[0]")
    public BaseResult<Brand> info(@PathVariable("brandId") Long brandId) {
        Brand brand = brandService.getById(brandId);
        return ObjectUtils.anyNotNull(brand) ? BaseResult.ok(brand) : BaseResult.fail();
    }

    /**
     * 批量获取品牌信息
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true, 失败返回false
     */
    @GetMapping("/infos/{brandIds}")
    public BaseResult<List<Brand>> brandsInfo(@PathVariable List<Long> brandIds) {
        List<Brand> brands = brandService.brandsInfo(brandIds);
        return CollectionUtils.isNotEmpty(brands) ? BaseResult.success(brands) : BaseResult.error();
    }

    /**
     * 新增品牌
     *
     * @param brand 品牌元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@Validated({InsertGroup.class}) @RequestBody Brand brand) {
        boolean save = brandService.save(brand);
        return BaseResult.ok(save);
    }

    /**
     * 修改品牌
     *
     * @param brand 品牌,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    @CacheEvict(value = "product:brand", allEntries = true)
    public BaseResult<Boolean> update(@Validated({UpdateGroup.class}) @RequestBody Brand brand) {
        boolean update = brandService.updateDetailById(brand);
        return toOperationResult(update);
    }

    /**
     * 修改品牌状态
     *
     * @param brand 品牌,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update/status")
    @CacheEvict(value = "product:brand", allEntries = true)
    public BaseResult<Boolean> updateStatus(@Validated({UpdateStatusGroup.class}) @RequestBody Brand brand) {
        boolean update = brandService.updateById(brand);
        return toOperationResult(update);
    }

    /**
     * 批量删除品牌
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{brandIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] brandIds) {
        boolean delete = brandService.removeByIds(Arrays.asList(brandIds));
        return toOperationResult(delete);
    }
}
