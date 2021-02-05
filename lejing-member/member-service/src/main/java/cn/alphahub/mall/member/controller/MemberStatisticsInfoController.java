package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.MemberStatisticsInfo;
import cn.alphahub.mall.member.service.MemberStatisticsInfoService;

import java.util.Arrays;

/**
 * 会员统计信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@RestController
@RequestMapping("member/memberstatisticsinfo")
public class MemberStatisticsInfoController extends BaseController {
    @Autowired
    private MemberStatisticsInfoService memberStatisticsInfoService;

    /**
     * 查询会员统计信息列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param memberStatisticsInfo 会员统计信息,字段选择性传入,默认等值查询
     * @return 会员统计信息分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberstatisticsinfo:list")
    public BaseResult<PageResult<MemberStatisticsInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberStatisticsInfo memberStatisticsInfo
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberStatisticsInfo> pageResult = memberStatisticsInfoService.queryPage(pageDomain, memberStatisticsInfo);
        return (BaseResult<PageResult<MemberStatisticsInfo>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员统计信息详情
     *
     * @param id 会员统计信息主键id
     * @return 会员统计信息详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberstatisticsinfo:info")
    public BaseResult<MemberStatisticsInfo> info(@PathVariable("id") Long id){
        MemberStatisticsInfo memberStatisticsInfo = memberStatisticsInfoService.getById(id);
        return (BaseResult<MemberStatisticsInfo>) toResponseResult(memberStatisticsInfo);
    }

    /**
     * 新增会员统计信息
     *
     * @param memberStatisticsInfo 会员统计信息元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:memberstatisticsinfo:save")
    public BaseResult<Boolean> save(/*@RequestBody*/ MemberStatisticsInfo memberStatisticsInfo) {
        boolean save = memberStatisticsInfoService.save(memberStatisticsInfo);
        return toOperationResult(save);
    }

    /**
     * 修改会员统计信息
     *
     * @param memberStatisticsInfo 会员统计信息,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("coupon:couponhistory:update")
    public BaseResult<Boolean> update(/*@RequestBody*/ MemberStatisticsInfo memberStatisticsInfo) {
        boolean update = memberStatisticsInfoService.updateById(memberStatisticsInfo);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员统计信息
     *
     * @param ids 会员统计信息id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:memberstatisticsinfo:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberStatisticsInfoService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
