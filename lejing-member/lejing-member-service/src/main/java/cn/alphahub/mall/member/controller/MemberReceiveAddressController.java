package cn.alphahub.mall.member.controller;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.member.service.MemberReceiveAddressService;

import java.util.Arrays;

/**
 * 会员收货地址Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@RestController
@RequestMapping("member/memberreceiveaddress")
public class MemberReceiveAddressController extends BaseController {
    @Autowired
    private MemberReceiveAddressService memberReceiveAddressService;

    /**
     * 查询会员收货地址列表
     *
     * @param page         当前页码,默认第1页
     * @param rows         显示行数,默认10条
     * @param orderColumn  排序排序字段,默认不排序
     * @param isAsc        排序方式,desc或者asc
     * @param memberReceiveAddress 会员收货地址,字段选择性传入,默认为等值查询
     * @return 会员收货地址分页数据
     */
    @GetMapping("/list")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberreceiveaddress:list")
    public BaseResult<PageResult<MemberReceiveAddress>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            MemberReceiveAddress memberReceiveAddress
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<MemberReceiveAddress> pageResult = memberReceiveAddressService.queryPage(pageDomain, memberReceiveAddress);
        return (BaseResult<PageResult<MemberReceiveAddress>>) toPageableResult(pageResult);
    }

    /**
     * 获取会员收货地址详情
     *
     * @param id 会员收货地址主键id
     * @return 会员收货地址详细信息
     */
    @GetMapping("/{id}")
    @SuppressWarnings("unchecked")
    //@RequiresPermissions("member:memberreceiveaddress:info")
    public BaseResult<MemberReceiveAddress> info(@PathVariable("id") Long id){
        MemberReceiveAddress memberReceiveAddress = memberReceiveAddressService.getById(id);
        return (BaseResult<MemberReceiveAddress>) toResponseResult(memberReceiveAddress);
    }

    /**
     * 新增会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址元数据
     * @return 成功返回true,失败返回false
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:memberreceiveaddress:save")
    public BaseResult<Boolean> save(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        boolean save = memberReceiveAddressService.save(memberReceiveAddress);
        return toOperationResult(save);
    }

    /**
     * 修改会员收货地址
     *
     * @param memberReceiveAddress 会员收货地址,根据主键id选择性更新
     * @return 成功返回true,失败返回false
     */
    @PutMapping("/update")
    //@RequiresPermissions("member:memberreceiveaddress:update")
    public BaseResult<Boolean> update(@RequestBody MemberReceiveAddress memberReceiveAddress) {
        boolean update = memberReceiveAddressService.updateById(memberReceiveAddress);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员收货地址
     *
     * @param ids 会员收货地址id集合
     * @return 成功返回true,失败返回false
     */
    @DeleteMapping("/{ids}")
    //@RequiresPermissions("member:memberreceiveaddress:delete")
    public BaseResult<Boolean> delete(@PathVariable Long[] ids){
        boolean delete = memberReceiveAddressService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }
}
