package cn.alphahub.mall.reserve.site.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.site.domain.SiteOrderRule;
import cn.alphahub.mall.reserve.site.service.SiteOrderRuleService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地预约订单规则表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@RestController
@RequestMapping("site/siteorderrule")
public class SiteOrderRuleController extends BaseController {
    @Autowired
    private SiteOrderRuleService siteOrderRuleService;

    /**
     * 查询场地预约订单规则表列表
     *
     * @param page          当前页码,默认第1页
     * @param rows          显示行数,默认10条
     * @param orderColumn   排序排序字段,默认不排序
     * @param isAsc         排序方式,desc或者asc
     * @param siteOrderRule 场地预约订单规则表, 查询字段选择性传入, 默认为等值查询
     * @return 场地预约订单规则表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SiteOrderRule>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SiteOrderRule siteOrderRule
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SiteOrderRule> pageResult = siteOrderRuleService.queryPage(pageDomain, siteOrderRule);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地预约订单规则表详情
     *
     * @param siteId 场地预约订单规则表主键id
     * @return 场地预约订单规则表详细信息
     */
    @GetMapping("/info/{siteId}")
    public BaseResult<SiteOrderRule> info(@PathVariable("siteId") Long siteId) {
        SiteOrderRule siteOrderRule = siteOrderRuleService.getById(siteId);
        return ObjectUtils.anyNotNull(siteOrderRule) ? BaseResult.ok(siteOrderRule) : BaseResult.fail();
    }

    /**
     * 新增场地预约订单规则表
     *
     * @param siteOrderRule 场地预约订单规则表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SiteOrderRule siteOrderRule) {
        boolean save = siteOrderRuleService.save(siteOrderRule);
        return toOperationResult(save);
    }

    /**
     * 修改场地预约订单规则表
     *
     * @param siteOrderRule 场地预约订单规则表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SiteOrderRule siteOrderRule) {
        boolean update = siteOrderRuleService.updateById(siteOrderRule);
        return toOperationResult(update);
    }

    /**
     * 批量删除场地预约订单规则表
     *
     * @param siteIds 场地预约订单规则表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{siteIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] siteIds) {
        boolean delete = siteOrderRuleService.removeByIds(Arrays.asList(siteIds));
        return toOperationResult(delete);
    }
}
