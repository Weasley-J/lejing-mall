package cn.alphahub.mall.member.api;

import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会员收货地址Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberReceiveAddressApi {

    /**
     * 查询会员收货地址列表
     *
     * @param page                 当前页码,默认第1页
     * @param rows                 显示行数,默认10条
     * @param orderColumn          排序排序字段,默认不排序
     * @param isAsc                排序方式,desc或者asc
     * @param memberReceiveAddress 会员收货地址, 查询字段选择性传入, 默认为等值查询
     * @return 会员收货地址分页数据
     */
    @GetMapping("member/memberreceiveaddress/list")
    Result<PageResult<MemberReceiveAddress>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberReceiveAddress memberReceiveAddress
    );

    /**
     * 查询用户的收货地址列表
     *
     * @param memberId 用户id
     * @return 收货地址列表
     */
    @GetMapping("member/memberreceiveaddress/addresses/{memberId}")
    Result<List<MemberReceiveAddress>> memberAddressList(@PathVariable("memberId") Long memberId);

    /**
     * 获取会员收货地址详情
     *
     * @param id 会员收货地址主键id
     * @return 会员收货地址详细信息
     */
    @GetMapping("member/memberreceiveaddress/info/{id}")
    Result<MemberReceiveAddress> info(@PathVariable("id") Long id);

    /**
     * 新增会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("member/memberreceiveaddress/save")
    Result<Boolean> save(@RequestBody MemberReceiveAddress memberReceiveAddress);

    /**
     * 修改会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("member/memberreceiveaddress/update")
    Result<Boolean> update(@RequestBody MemberReceiveAddress memberReceiveAddress);

    /**
     * 批量删除会员收货地址
     *
     * @param ids 会员收货地址id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("member/memberreceiveaddress/delete/{ids}")
    Result<Boolean> delete(@PathVariable Long[] ids);
}
