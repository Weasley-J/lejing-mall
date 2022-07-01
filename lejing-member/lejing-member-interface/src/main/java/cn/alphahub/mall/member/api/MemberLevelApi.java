package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLevel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员等级Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberLevelApi {
    /**
     * 查询会员等级列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param memberLevel 会员等级, 查询字段选择性传入, 默认为等值查询
     * @return 会员等级分页数据
     */
    @GetMapping("/member/memberlevel/list")
    Result<PageResult<MemberLevel>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberLevel memberLevel
    );

    /**
     * 获取会员等级详情
     *
     * @param id 会员等级主键id
     * @return 会员等级详细信息
     */
    @GetMapping("/member/memberlevel/info/{id}")
    Result<MemberLevel> info(@PathVariable("id") Long id);

    /**
     * 新增会员等级
     *
     * @param memberLevel 会员等级元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/member/memberlevel/save")
    Result<Boolean> save(@RequestBody MemberLevel memberLevel);

    /**
     * 修改会员等级
     *
     * @param memberLevel 会员等级, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/member/memberlevel/update")
    Result<Boolean> update(@RequestBody MemberLevel memberLevel);

    /**
     * 批量删除会员等级
     *
     * @param ids 会员等级id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/member/memberlevel/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
