package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.coupon.domain.HomeSubjectSpu;
import cn.alphahub.mall.coupon.service.HomeSubjectSpuService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 专题商品Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/homesubjectspu")
public class HomeSubjectSpuController {
    @Resource
    private HomeSubjectSpuService homeSubjectSpuService;

    /**
     * 查询专题商品列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param homeSubjectSpu 专题商品, 查询字段选择性传入, 默认为等值查询
     * @return 专题商品分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<HomeSubjectSpu>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            HomeSubjectSpu homeSubjectSpu
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<HomeSubjectSpu> pageResult = homeSubjectSpuService.queryPage(pageDomain, homeSubjectSpu);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取专题商品详情
     *
     * @param id 专题商品主键id
     * @return 专题商品详细信息
     */
    @GetMapping("/info/{id}")
    public Result<HomeSubjectSpu> info(@PathVariable("id") Long id) {
        HomeSubjectSpu homeSubjectSpu = homeSubjectSpuService.getById(id);
        return ObjectUtils.anyNotNull(homeSubjectSpu) ? Result.of(homeSubjectSpu) : Result.fail();
    }

    /**
     * 新增专题商品
     *
     * @param homeSubjectSpu 专题商品元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody HomeSubjectSpu homeSubjectSpu) {
        boolean save = homeSubjectSpuService.save(homeSubjectSpu);
        return Result.of(save);
    }

    /**
     * 修改专题商品
     *
     * @param homeSubjectSpu 专题商品, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody HomeSubjectSpu homeSubjectSpu) {
        boolean update = homeSubjectSpuService.updateById(homeSubjectSpu);
        return Result.of(update);
    }

    /**
     * 批量删除专题商品
     *
     * @param ids 专题商品id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = homeSubjectSpuService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
