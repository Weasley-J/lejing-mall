package cn.alphahub.mall.product.api;

import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.valid.group.Edit;
import cn.alphahub.common.valid.group.EditStatus;
import cn.alphahub.common.valid.group.Insert;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.product.domain.Brand;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 品牌Controller远程调用接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
public interface BrandApi {
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
    @GetMapping("product/brand/list")
    Result<PageResult<Brand>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            @RequestParam(value = "key", defaultValue = "") String key,
            Brand brand
    );

    /**
     * 获取品牌详情
     *
     * @param brandId 品牌主键id
     * @return 品牌详细信息
     */
    @GetMapping("product/brand/info/{brandId}")
    Result<Brand> info(@PathVariable("brandId") Long brandId);

    /**
     * 批量获取品牌信息
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true, 失败返回false
     */
    @GetMapping("product/brand/infos/{brandIds}")
    Result<List<Brand>> brandsInfo(@PathVariable List<Long> brandIds);

    /**
     * 新增品牌
     *
     * @param brand 品牌元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("product/brand/save")
    Result<Boolean> save(@Validated({Insert.class}) @RequestBody Brand brand);

    /**
     * 修改品牌
     *
     * @param brand 品牌,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("product/brand/update")
    Result<Boolean> update(@Validated({Edit.class}) @RequestBody Brand brand);

    /**
     * 修改品牌状态
     *
     * @param brand 品牌,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("product/brand/update/status")
    Result<Boolean> updateStatus(@Validated({EditStatus.class}) @RequestBody Brand brand);

    /**
     * 批量删除品牌
     *
     * @param brandIds 品牌id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("product/brand/delete/{brandIds}")
    Result<Boolean> delete(@PathVariable Long[] brandIds);
}
