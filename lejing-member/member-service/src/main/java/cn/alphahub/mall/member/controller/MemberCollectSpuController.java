package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.MemberCollectSpu;
import cn.alphahub.mall.member.service.MemberCollectSpuService;

import java.util.Arrays;

/**
 * 会员收藏的商品Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@RestController
@RequestMapping("member/membercollectspu")
public class MemberCollectSpuController extends BaseController {
    @Autowired
    private MemberCollectSpuService memberCollectSpuService;

    /**
     * 查询会员收藏的商品列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param memberCollectSpu 会员收藏的商品,字段选择性传入,默认等值查询
     * @return 会员收藏的商品分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:membercollectspu:list")
    public BaseResult<PageResult<MemberCollectSpu>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberCollectSpu memberCollectSpu
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberCollectSpu> pageResult = memberCollectSpuService.queryPage(pageDomain, memberCollectSpu);
        return (BaseResult<PageResult<MemberCollectSpu>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员收藏的商品详情
     *
     * @param id 会员收藏的商品主键id
     * @return 会员收藏的商品详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:membercollectspu:info")
    public BaseResult<MemberCollectSpu> info(@PathVariable("id") Long id){
        MemberCollectSpu memberCollectSpu = memberCollectSpuService.getById(id);
        return (BaseResult<MemberCollectSpu>) toResponseResult(memberCollectSpu);
    }

    /**
     * 新增会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:membercollectspu:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ MemberCollectSpu memberCollectSpu) {
        boolean save = memberCollectSpuService.save(memberCollectSpu);
        return toOperationResult(save);
    }

    /**
     * 修改会员收藏的商品
     *
     * @param memberCollectSpu 会员收藏的商品,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ MemberCollectSpu memberCollectSpu) {
        boolean update = memberCollectSpuService.updateById(memberCollectSpu);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员收藏的商品
     *
     * @param ids 会员收藏的商品id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:membercollectspu:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberCollectSpuService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
