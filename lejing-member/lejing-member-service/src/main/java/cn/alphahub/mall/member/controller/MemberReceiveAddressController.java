package cn.alphahub.mall.member.controller;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.member.service.MemberReceiveAddressService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 会员收货地址Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController extends BaseController {
    @Resource
    private MemberReceiveAddressService memberReceiveAddressService;

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
    @GetMapping("/list")
    public Result<PageResult<MemberReceiveAddress>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberReceiveAddress memberReceiveAddress
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberReceiveAddress> pageResult = memberReceiveAddressService.queryPage(pageDomain, memberReceiveAddress);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取会员收货地址详情
     *
     * @param id 会员收货地址主键id
     * @return 会员收货地址详细信息
     */
    @GetMapping("/info/{id}")
    public Result<MemberReceiveAddress> info(@PathVariable("id") Long id) {
        MemberReceiveAddress memberReceiveAddress = memberReceiveAddressService.getById(id);
        return ObjectUtils.anyNotNull(memberReceiveAddress) ? Result.ok(memberReceiveAddress) : Result.fail();
    }

    /**
     * 新增会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        boolean save = memberReceiveAddressService.save(memberReceiveAddress);
        return toOperationResult(save);
    }

    /**
     * 修改会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @GetMapping("/update")
    public Result<Boolean> update(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        boolean update = memberReceiveAddressService.updateById(memberReceiveAddress);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员收货地址
     *
     * @param ids 会员收货地址id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = memberReceiveAddressService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }

    /**
     * 查询用户的收货地址列表
     *
     * @param memberId 用户id
     * @return 收货地址列表
     */
    @GetMapping("/addresses/{memberId}")
    Result<List<MemberReceiveAddress>> memberAddressList(@PathVariable(value = "memberId") Long memberId) {
        return Result.success(memberReceiveAddressService.memberAddressList(memberId));
    }
}
