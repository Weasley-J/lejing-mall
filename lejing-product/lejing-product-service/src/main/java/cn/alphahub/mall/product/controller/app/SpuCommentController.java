package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.SpuComment;
import cn.alphahub.mall.product.service.SpuCommentService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 商品评价Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/spucomment")
public class SpuCommentController extends BaseController {
    @Resource
    private SpuCommentService spuCommentService;

    /**
     * 查询商品评价列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param spuComment  商品评价,查询字段选择性传入,默认为等值查询
     * @return 商品评价分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<SpuComment>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            SpuComment spuComment
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<SpuComment> pageResult = spuCommentService.queryPage(pageDomain, spuComment);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品评价详情
     *
     * @param id 商品评价主键id
     * @return 商品评价详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<SpuComment> info(@PathVariable("id") Long id) {
        SpuComment spuComment = spuCommentService.getById(id);
        return ObjectUtils.anyNotNull(spuComment) ? BaseResult.ok(spuComment) : BaseResult.fail();
    }

    /**
     * 新增商品评价
     *
     * @param spuComment 商品评价元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody SpuComment spuComment) {
        boolean save = spuCommentService.save(spuComment);
        return toOperationResult(save);
    }

    /**
     * 修改商品评价
     *
     * @param spuComment 商品评价,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody SpuComment spuComment) {
        boolean update = spuCommentService.updateById(spuComment);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品评价
     *
     * @param ids 商品评价id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = spuCommentService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
