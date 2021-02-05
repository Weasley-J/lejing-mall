package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.service.MemberService;

import java.util.Arrays;

/**
 * 会员Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@RestController
@RequestMapping("member/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberService memberService;

    /**
     * 查询会员列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param member 会员,字段选择性传入,默认等值查询
     * @return 会员分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:member:list")
    public BaseResult<PageResult<Member>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Member member
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Member> pageResult = memberService.queryPage(pageDomain, member);
        return (BaseResult<PageResult<Member>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员详情
     *
     * @param id 会员主键id
     * @return 会员详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:member:info")
    public BaseResult<Member> info(@PathVariable("id") Long id){
        Member member = memberService.getById(id);
        return (BaseResult<Member>) toResponseResult(member);
    }

    /**
     * 新增会员
     *
     * @param member 会员元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:member:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ Member member) {
        boolean save = memberService.save(member);
        return toOperationResult(save);
    }

    /**
     * 修改会员
     *
     * @param member 会员,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ Member member) {
        boolean update = memberService.updateById(member);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员
     *
     * @param ids 会员id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:member:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
