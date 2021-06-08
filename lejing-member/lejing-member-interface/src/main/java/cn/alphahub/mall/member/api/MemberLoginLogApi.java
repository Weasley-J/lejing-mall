package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import org.springframework.web.bind.annotation.*;

/**
 * 会员登录记录Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RequestMapping("member/memberloginlog")
public interface MemberLoginLogApi {

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
    BaseResult<PageResult<MemberLoginLog>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberLoginLog memberLoginLog
    );

    /**
     * 获取会员登录记录详情
     *
     * @param id 会员登录记录主键id
     * @return 会员登录记录详细信息
     */
    @GetMapping("/info/{id}")
    BaseResult<MemberLoginLog> info(@PathVariable("id") Long id);

    /**
     * 新增会员登录记录
     *
     * @param memberLoginLog 会员登录记录元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@RequestBody MemberLoginLog memberLoginLog);

    /**
     * 修改会员登录记录
     *
     * @param memberLoginLog 会员登录记录, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    BaseResult<Boolean> update(@RequestBody MemberLoginLog memberLoginLog);

    /**
     * 批量删除会员登录记录
     *
     * @param ids 会员登录记录id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    BaseResult<Boolean> delete(@PathVariable Long[] ids);
}
