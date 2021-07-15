package cn.alphahub.mall.site.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.site.domain.SiteInvalidSession;
import cn.alphahub.mall.site.service.SiteInvalidSessionService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 场地预约不可用场次表Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@RestController
@RequestMapping("site/siteinvalidsession")
public class SiteInvalidSessionController extends BaseController {
    @Autowired
    private SiteInvalidSessionService siteInvalidSessionService;

    /**
     * 查询场地预约不可用场次表列表
     *
     * @param page               当前页码,默认第1页
     * @param rows               显示行数,默认10条
     * @param orderColumn        排序排序字段,默认不排序
     * @param isAsc              排序方式,desc或者asc
     * @param siteInvalidSession 场地预约不可用场次表, 查询字段选择性传入, 默认为等值查询
     * @return 场地预约不可用场次表分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SiteInvalidSession>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SiteInvalidSession siteInvalidSession
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SiteInvalidSession> pageResult = siteInvalidSessionService.queryPage(pageDomain, siteInvalidSession);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取场地预约不可用场次表详情
     *
     * @param invalidId 场地预约不可用场次表主键id
     * @return 场地预约不可用场次表详细信息
     */
    @GetMapping("/info/{invalidId}")
    public BaseResult<SiteInvalidSession> info(@PathVariable("invalidId") Long invalidId) {
        SiteInvalidSession siteInvalidSession = siteInvalidSessionService.getById(invalidId);
        return ObjectUtils.anyNotNull(siteInvalidSession) ? BaseResult.ok(siteInvalidSession) : BaseResult.fail();
    }

    /**
     * 新增场地预约不可用场次表
     *
     * @param siteInvalidSession 场地预约不可用场次表元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SiteInvalidSession siteInvalidSession) {
        boolean save = siteInvalidSessionService.save(siteInvalidSession);
        return toOperationResult(save);
    }

    /**
     * 修改场地预约不可用场次表
     *
     * @param siteInvalidSession 场地预约不可用场次表, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SiteInvalidSession siteInvalidSession) {
        boolean update = siteInvalidSessionService.updateById(siteInvalidSession);
        return toOperationResult(update);
    }

    /**
     * 批量删除场地预约不可用场次表
     *
     * @param invalidIds 场地预约不可用场次表id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{invalidIds}")
    public BaseResult<Boolean> delete(@PathVariable Long[] invalidIds) {
        boolean delete = siteInvalidSessionService.removeByIds(Arrays.asList(invalidIds));
        return toOperationResult(delete);
    }
}
