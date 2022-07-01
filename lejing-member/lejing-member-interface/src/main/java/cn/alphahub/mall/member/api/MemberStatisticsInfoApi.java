package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberStatisticsInfo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 会员统计信息Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberStatisticsInfoApi {
    /**
     * 查询会员统计信息列表
     *
     * @param page                 当前页码,默认第1页
     * @param rows                 显示行数,默认10条
     * @param orderColumn          排序排序字段,默认不排序
     * @param isAsc                排序方式,desc或者asc
     * @param memberStatisticsInfo 会员统计信息, 查询字段选择性传入, 默认为等值查询
     * @return 会员统计信息分页数据
     */
    @GetMapping("member/memberstatisticsinfo/list")
    Result<PageResult<MemberStatisticsInfo>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberStatisticsInfo memberStatisticsInfo
    );

    /**
     * 获取会员统计信息详情
     *
     * @param id 会员统计信息主键id
     * @return 会员统计信息详细信息
     */
    @GetMapping("member/memberstatisticsinfo/info/{id}")
    Result<MemberStatisticsInfo> info(@PathVariable("id") Long id);

    /**
     * 新增会员统计信息
     *
     * @param memberStatisticsInfo 会员统计信息元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("member/memberstatisticsinfo/save")
    Result<Boolean> save(@RequestBody MemberStatisticsInfo memberStatisticsInfo);

    /**
     * 修改会员统计信息
     *
     * @param memberStatisticsInfo 会员统计信息, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("member/memberstatisticsinfo/update")
    Result<Boolean> update(@RequestBody MemberStatisticsInfo memberStatisticsInfo);

    /**
     * 批量删除会员统计信息
     *
     * @param ids 会员统计信息id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("member/memberstatisticsinfo/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
