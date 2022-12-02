package cn.alphahub.mall.member.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.member.domain.MemberCollectSpu;
import cn.alphahub.mall.member.service.MemberCollectSpuService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 会员收藏的商品Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RestController
@RequestMapping("member/membercollectspu")
public class MemberCollectSpuController {
    @Resource
    private MemberCollectSpuService memberCollectSpuService;

    /**
     * 查询会员收藏的商品列表
     *
     * @param page             当前页码,默认第1页
     * @param rows             显示行数,默认10条
     * @param orderColumn      排序排序字段,默认不排序
     * @param isAsc            排序方式,desc或者asc
     * @param memberCollectSpu 会员收藏的商品, 查询字段选择性传入, 默认为等值查询
     * @return 会员收藏的商品分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<MemberCollectSpu>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberCollectSpu memberCollectSpu
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberCollectSpu> pageResult = memberCollectSpuService.queryPage(pageDomain, memberCollectSpu);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.of(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取会员收藏的商品详情
     *
     * @param id 会员收藏的商品主键id
     * @return 会员收藏的商品详细信息
     */
    @GetMapping("/info/{id}")
    public Result<MemberCollectSpu> info(@PathVariable("id") Long id) {
        MemberCollectSpu memberCollectSpu = memberCollectSpuService.getById(id);
        return ObjectUtils.anyNotNull(memberCollectSpu) ? Result.of(memberCollectSpu) : Result.fail();
    }

    /**
     * 新增会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody MemberCollectSpu memberCollectSpu) {
        boolean save = memberCollectSpuService.save(memberCollectSpu);
        return Result.of(save);
    }

    /**
     * 修改会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody MemberCollectSpu memberCollectSpu) {
        boolean update = memberCollectSpuService.updateById(memberCollectSpu);
        return Result.of(update);
    }

    /**
     * 批量删除会员收藏的商品
     *
     * @param ids 会员收藏的商品id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = memberCollectSpuService.removeByIds(Arrays.asList(ids));
        return Result.of(delete);
    }
}
