package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.MemberCollectSubject;
import cn.alphahub.mall.member.service.MemberCollectSubjectService;

import java.util.Arrays;

/**
 * 会员收藏的专题活动Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@RestController
@RequestMapping("member/membercollectsubject")
public class MemberCollectSubjectController extends BaseController {
    @Autowired
    private MemberCollectSubjectService memberCollectSubjectService;

    /**
     * 查询会员收藏的专题活动列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param memberCollectSubject 会员收藏的专题活动,字段选择性传入,默认等值查询
     * @return 会员收藏的专题活动分页数据
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:membercollectsubject:list")
    public BaseResult<PageResult<MemberCollectSubject>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberCollectSubject memberCollectSubject
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberCollectSubject> pageResult = memberCollectSubjectService.queryPage(pageDomain, memberCollectSubject);
        return (BaseResult<PageResult<MemberCollectSubject>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员收藏的专题活动详情
     *
     * @param id 会员收藏的专题活动主键id
     * @return 会员收藏的专题活动详细信息
     */
    @GetMapping("/{id}")
    public BaseResult<MemberCollectSubject> info(@PathVariable("id") Long id){
        MemberCollectSubject memberCollectSubject = memberCollectSubjectService.getById(id);
        return (BaseResult<MemberCollectSubject>) toResponseResult(memberCollectSubject);
    }

    /**
     * 新增会员收藏的专题活动
     *
     * @param memberCollectSubject 会员收藏的专题活动元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:membercollectsubject:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ MemberCollectSubject memberCollectSubject) {
        boolean save = memberCollectSubjectService.save(memberCollectSubject);
        return toOperationResult(save);
    }

    /**
     * 修改会员收藏的专题活动
     *
     * @param memberCollectSubject 会员收藏的专题活动,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    public BaseResult<Boolean> update(/*@RequestBody*/ MemberCollectSubject memberCollectSubject) {
        boolean update = memberCollectSubjectService.updateById(memberCollectSubject);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员收藏的专题活动
     *
     * @param ids 会员收藏的专题活动id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:membercollectsubject:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberCollectSubjectService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}