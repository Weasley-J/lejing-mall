package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.AttrAttrgroupRelation;
import cn.alphahub.mall.product.service.AttrAttrgroupRelationService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 属性&属性分组关联Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/attrattrgrouprelation")
public class AttrAttrgroupRelationController extends BaseController {
    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    /**
     * 查询属性&属性分组关联列表
     *
     * @param page                  当前页码,默认第1页
     * @param rows                  显示行数,默认10条
     * @param orderColumn           排序排序字段,默认不排序
     * @param isAsc                 排序方式,desc或者asc
     * @param attrAttrgroupRelation 属性&属性分组关联,查询字段选择性传入,默认为等值查询
     * @return 属性&属性分组关联分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<AttrAttrgroupRelation>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            AttrAttrgroupRelation attrAttrgroupRelation
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<AttrAttrgroupRelation> pageResult = attrAttrgroupRelationService.queryPage(pageDomain, attrAttrgroupRelation);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取属性&属性分组关联详情
     *
     * @param id 属性&属性分组关联主键id
     * @return 属性&属性分组关联详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<AttrAttrgroupRelation> info(@PathVariable("id") Long id) {
        AttrAttrgroupRelation attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);
        return ObjectUtils.anyNotNull(attrAttrgroupRelation) ? BaseResult.ok(attrAttrgroupRelation) : BaseResult.fail();
    }

    /**
     * 新增属性&属性分组关联
     *
     * @param attrAttrgroupRelation 属性&属性分组关联元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
        boolean save = attrAttrgroupRelationService.save(attrAttrgroupRelation);
        return toOperationResult(save);
    }

    /**
     * 修改属性&属性分组关联
     *
     * @param attrAttrgroupRelation 属性&属性分组关联,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody AttrAttrgroupRelation attrAttrgroupRelation) {
        boolean update = attrAttrgroupRelationService.updateById(attrAttrgroupRelation);
        return toOperationResult(update);
    }

    /**
     * 批量删除属性&属性分组关联
     *
     * @param ids 属性&属性分组关联id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
