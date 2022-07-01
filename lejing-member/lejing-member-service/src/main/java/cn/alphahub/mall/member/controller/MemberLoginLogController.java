package cn.alphahub.mall.member.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import cn.alphahub.mall.member.service.MemberLoginLogService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 会员登录记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RestController
@RequestMapping("member/memberloginlog")
public class MemberLoginLogController extends BaseController {
    @Resource
    private MemberLoginLogService memberLoginLogService;

    /**
     * 查询会员登录记录列表
     *
     * @param page           当前页码,默认第1页
     * @param rows           显示行数,默认10条
     * @param orderColumn    排序排序字段,默认不排序
     * @param isAsc          排序方式,desc或者asc
     * @param memberLoginLog 会员登录记录, 查询字段选择性传入, 默认为等值查询
     * @return 会员登录记录分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<MemberLoginLog>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberLoginLog memberLoginLog
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberLoginLog> pageResult = memberLoginLogService.queryPage(pageDomain, memberLoginLog);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取会员登录记录详情
     *
     * @param id 会员登录记录主键id
     * @return 会员登录记录详细信息
     */
    @GetMapping("/info/{id}")
    public Result<MemberLoginLog> info(@PathVariable("id") Long id) {
        MemberLoginLog memberLoginLog = memberLoginLogService.getById(id);
        return ObjectUtils.anyNotNull(memberLoginLog) ? Result.ok(memberLoginLog) : Result.fail();
    }

    /**
     * 新增会员登录记录
     *
     * @param memberLoginLog 会员登录记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody MemberLoginLog memberLoginLog) {
        boolean save = memberLoginLogService.save(memberLoginLog);
        return toOperationResult(save);
    }

    /**
     * 修改会员登录记录
     *
     * @param memberLoginLog 会员登录记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody MemberLoginLog memberLoginLog) {
        boolean update = memberLoginLogService.updateById(memberLoginLog);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员登录记录
     *
     * @param ids 会员登录记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = memberLoginLogService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
