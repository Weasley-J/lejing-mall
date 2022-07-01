package cn.alphahub.mall.coupon.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeSubject;
import cn.alphahub.mall.coupon.service.HomeSubjectService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@RestController
@RequestMapping("coupon/homesubject")
public class HomeSubjectController extends BaseController {
    @Resource
    private HomeSubjectService homeSubjectService;

    /**
     * 查询首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param homeSubject 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】, 查询字段选择性传入, 默认为等值查询
     * @return 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<HomeSubject>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            HomeSubject homeSubject
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<HomeSubject> pageResult = homeSubjectService.queryPage(pageDomain, homeSubject);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】详情
     *
     * @param id 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】主键id
     * @return 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】详细信息
     */
    @GetMapping("/info/{id}")
    public Result<HomeSubject> info(@PathVariable("id") Long id) {
        HomeSubject homeSubject = homeSubjectService.getById(id);
        return ObjectUtils.anyNotNull(homeSubject) ? Result.ok(homeSubject) : Result.fail();
    }

    /**
     * 新增首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
     *
     * @param homeSubject 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody HomeSubject homeSubject) {
        boolean save = homeSubjectService.save(homeSubject);
        return toOperationResult(save);
    }

    /**
     * 修改首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
     *
     * @param homeSubject 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody HomeSubject homeSubject) {
        boolean update = homeSubjectService.updateById(homeSubject);
        return toOperationResult(update);
    }

    /**
     * 批量删除首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
     *
     * @param ids 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = homeSubjectService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
