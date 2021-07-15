package cn.alphahub.mall.product.controller.app;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.product.domain.CommentReplay;
import cn.alphahub.mall.product.service.CommentReplayService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 商品评价回复关系Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:36:31
 */
@RestController
@RequestMapping("product/commentreplay")
public class CommentReplayController extends BaseController {
    @Resource
    private CommentReplayService commentReplayService;

    /**
     * 查询商品评价回复关系列表
     *
     * @param page          当前页码,默认第1页
     * @param rows          显示行数,默认10条
     * @param orderColumn   排序排序字段,默认不排序
     * @param isAsc         排序方式,desc或者asc
     * @param commentReplay 商品评价回复关系,查询字段选择性传入,默认为等值查询
     * @return 商品评价回复关系分页数据
     */
    @GetMapping("/list")
    public BaseResult<PageResult<CommentReplay>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            CommentReplay commentReplay
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<CommentReplay> pageResult = commentReplayService.queryPage(pageDomain, commentReplay);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return BaseResult.ok(pageResult);
        }
        return BaseResult.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取商品评价回复关系详情
     *
     * @param id 商品评价回复关系主键id
     * @return 商品评价回复关系详细信息
     */
    @GetMapping("/info/{id}")
    public BaseResult<CommentReplay> info(@PathVariable("id") Long id) {
        CommentReplay commentReplay = commentReplayService.getById(id);
        return ObjectUtils.anyNotNull(commentReplay) ? BaseResult.ok(commentReplay) : BaseResult.fail();
    }

    /**
     * 新增商品评价回复关系
     *
     * @param commentReplay 商品评价回复关系元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public BaseResult<Boolean> save(@RequestBody CommentReplay commentReplay) {
        boolean save = commentReplayService.save(commentReplay);
        return toOperationResult(save);
    }

    /**
     * 修改商品评价回复关系
     *
     * @param commentReplay 商品评价回复关系,根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(@RequestBody CommentReplay commentReplay) {
        boolean update = commentReplayService.updateById(commentReplay);
        return toOperationResult(update);
    }

    /**
     * 批量删除商品评价回复关系
     *
     * @param ids 商品评价回复关系id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = commentReplayService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
