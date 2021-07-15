package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuInfo;
import cn.alphahub.mall.product.feign.SpuBoundsClient;
import cn.alphahub.mall.product.service.SpuInfoService;
import cn.alphahub.mall.product.vo.SpuSaveVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * spu信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/spuinfo")
public class SpuInfoController extends BaseController {
    @Resource
    private SpuInfoService spuInfoService;
    @Autowired
    private SpuBoundsClient spuBoundsClient;

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
    @GetMapping("/list")
    public BaseResult<PageResult<SpuInfo>> list(
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
    ) {
        /*
        {
            page: 1,//当前页码
            rows: 10,//每页记录数
            sidx: 'id',//排序字段
            order: 'asc/desc',//排序方式
            key: '华为',//检索关键字
            catelogId: 6,//三级分类id
            brandId: 1,//品牌id
            status: 0,//商品状态
        }
        */
        PageDomain pageDomain;
        if (StringUtils.isAllBlank(sidx, order)) {
            pageDomain = new PageDomain(page, rows, sidx, order);
        } else {
            pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        }
        PageResult<SpuInfo> pageResult = spuInfoService.queryPage(pageDomain, spuInfo, key, catelogId, brandId, status);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取spu信息详情
     *
     * @param id spu信息主键id
     * @return spu信息详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SpuInfo> info(@PathVariable("id") Long id) {
        SpuInfo spuInfo = spuInfoService.getById(id);
        return ObjectUtils.anyNotNull(spuInfo) ? BaseResult.ok(spuInfo) : BaseResult.fail();
    }

    /**
     * 新增spu信息
     *
     * @param spuSaveVO spu信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SpuSaveVO spuSaveVO) {
        spuInfoService.saveSpuInfo(spuSaveVO);
        return BaseResult.ok();
    }

    /**
     * 上架商品
     *
     * @param spuId 商品spu id
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/{spuId}/up")
    public BaseResult<Boolean> spuOnShelves(@PathVariable("spuId") Long spuId) {
        boolean OnShelves = spuInfoService.spuOnShelves(spuId);
        return BaseResult.ok(OnShelves);
    }

    /**
     * 修改spu信息
     *
     * @param spuInfo spu信息,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SpuInfo spuInfo) {
        boolean update = spuInfoService.updateById(spuInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除spu信息
     *
     * @param ids spu信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = spuInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
