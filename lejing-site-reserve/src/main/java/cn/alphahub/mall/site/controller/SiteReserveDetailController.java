package cn.alphahub.mall.site.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.site.domain.SiteReserveDetail;
import cn.alphahub.mall.site.service.SiteReserveDetailService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地详情表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@RestController
@RequestMapping("site/sitereservedetail")
public class SiteReserveDetailController extends BaseController {
    @Autowired
    private SiteReserveDetailService siteReserveDetailService;

    /**
     * 查询场地详情表列表
     *
     * @param page              当前页码,默认第1页
     * @param rows              显示行数,默认10条
     * @param orderColumn       排序排序字段,默认不排序
     * @param isAsc             排序方式,desc或者asc
     * @param siteReserveDetail 场地详情表, 查询字段选择性传入, 默认为等值查询
     * @return 场地详情表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SiteReserveDetail>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SiteReserveDetail siteReserveDetail
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SiteReserveDetail> pageResult = siteReserveDetailService.queryPage(pageDomain, siteReserveDetail);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地详情表详情
     *
     * @param detailId 场地详情表主键id
     * @return 场地详情表详细信息
     */
    @GetMapping("/info/{detailId}")
    public BaseResult<SiteReserveDetail> info(@PathVariable("detailId") Long detailId) {
        SiteReserveDetail siteReserveDetail = siteReserveDetailService.getById(detailId);
        return ObjectUtils.anyNotNull(siteReserveDetail) ? BaseResult.ok(siteReserveDetail) : BaseResult.fail();
    }

    /**
     * 新增场地详情表
     *
     * @param siteReserveDetail 场地详情表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SiteReserveDetail siteReserveDetail) {
        boolean save = siteReserveDetailService.save(siteReserveDetail);
        return toOperationResult(save);
    }

    /**
     * 修改场地详情表
     *
     * @param siteReserveDetail 场地详情表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SiteReserveDetail siteReserveDetail) {
        boolean update = siteReserveDetailService.updateById(siteReserveDetail);
        return toOperationResult(update);
    }

    /**
     * 批量删除场地详情表
     *
     * @param detailIds 场地详情表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{detailIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] detailIds) {
        boolean delete = siteReserveDetailService.removeByIds(Arrays.asList(detailIds));
        return toOperationResult(delete);
    }
}
