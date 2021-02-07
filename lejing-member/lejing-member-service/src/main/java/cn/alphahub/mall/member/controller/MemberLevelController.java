package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.MemberLevel;
import cn.alphahub.mall.member.service.MemberLevelService;

import java.util.Arrays;

/**
 * 会员等级Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@RestController
@RequestMapping("member/memberlevel")
public class MemberLevelController extends BaseController {
    @Autowired
    private MemberLevelService memberLevelService;

    /**
     * 查询会员等级列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param memberLevel 会员等级,字段选择性传入,默认为等值查询
     * @return 会员等级分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberlevel:list")
    public BaseResult<PageResult<MemberLevel>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberLevel memberLevel
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberLevel> pageResult = memberLevelService.queryPage(pageDomain, memberLevel);
        return (BaseResult<PageResult<MemberLevel>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员等级详情
     *
     * @param id 会员等级主键id
     * @return 会员等级详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberlevel:info")
    public BaseResult<MemberLevel> info(@PathVariable("id") Long id){
        MemberLevel memberLevel = memberLevelService.getById(id);
        return (BaseResult<MemberLevel>) toResponseResult(memberLevel);
    }

    /**
     * 新增会员等级
     *
     * @param memberLevel 会员等级元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:memberlevel:save")
    public BaseResult<Boolean> save(@RequestBody MemberLevel memberLevel) {
        boolean save = memberLevelService.save(memberLevel);
        return toOperationResult(save);
    }

    /**
     * 修改会员等级
     *
     * @param memberLevel 会员等级,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:memberlevel:update")
    public BaseResult<Boolean> update(@RequestBody MemberLevel memberLevel) {
        boolean update = memberLevelService.updateById(memberLevel);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员等级
     *
     * @param ids 会员等级id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:memberlevel:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberLevelService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
