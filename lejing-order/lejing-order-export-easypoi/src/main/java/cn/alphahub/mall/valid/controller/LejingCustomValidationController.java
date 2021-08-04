package cn.alphahub.mall.valid.controller;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.valid.group.EditGroup;
import cn.alphahub.common.valid.group.EditStatusGroup;
import cn.alphahub.common.valid.group.InsertGroup;
import cn.alphahub.common.valid.group.QueryGroup;
import cn.alphahub.mall.valid.domain.VirtualCoin;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义注解校验示例Controller
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/08/04
 */
@Slf4j
@RestController
@RequestMapping("/order/public/valid")
public class LejingCustomValidationController {

    private static final Map<Long, VirtualCoin> COIN_MAP = new LinkedHashMap<>();

    static {
        String income = "INCOME";
        String expenditure = "EXPENDITURE";
        COIN_MAP.put(1L, new VirtualCoin().setMemberId(1L).setVirtualValue(new BigDecimal("8881")).setIncomeExpenditureType(income).setStatus(-1));
        COIN_MAP.put(2L, new VirtualCoin().setMemberId(2L).setVirtualValue(new BigDecimal("8882")).setIncomeExpenditureType(expenditure).setStatus(0));
        COIN_MAP.put(3L, new VirtualCoin().setMemberId(3L).setVirtualValue(new BigDecimal("8883")).setIncomeExpenditureType(income).setStatus(1));
        COIN_MAP.put(4L, new VirtualCoin().setMemberId(4L).setVirtualValue(new BigDecimal("8884")).setIncomeExpenditureType(expenditure).setStatus(-1));
        COIN_MAP.put(5L, new VirtualCoin().setMemberId(5L).setVirtualValue(new BigDecimal("8885")).setIncomeExpenditureType(income).setStatus(1));
    }

    /**
     * 保存用户的虚拟币
     *
     * @param virtualCoin 虚拟币 - 数据对象
     * @return 保存结果
     */
    @PostMapping("/save")
    public BaseResult<VirtualCoin> save(@Validated({InsertGroup.class}) @RequestBody VirtualCoin virtualCoin) {
        log.info("保存用户【{}】的虚拟币了，入参：{}", virtualCoin.getMemberId(), JSONUtil.toJsonPrettyStr(virtualCoin));
        return BaseResult.ok(virtualCoin);
    }

    /**
     * 获取会员的虚拟货币列表
     *
     * @return 虚拟货币列表
     */
    @GetMapping("/list")
    public BaseResult<List<VirtualCoin>> list() {
        log.info("获取会员的虚拟货币列表");
        List<VirtualCoin> virtualCoins = Lists.newArrayList();
        COIN_MAP.forEach((memberId, virtualCoin) -> virtualCoins.add(virtualCoin));
        return BaseResult.ok(virtualCoins);
    }

    /**
     * 获取会员的虚拟货币明细
     *
     * @param memberId 会员id
     * @return 会员的虚拟货币明细
     */
    @GetMapping("/info/{memberId}")
    public BaseResult<VirtualCoin> info(@Validated({QueryGroup.class}) @PathVariable("memberId") Long memberId) {
        log.info("获取会员的虚拟货币明细:{}", memberId);
        return BaseResult.ok(COIN_MAP.get(memberId));
    }

    /**
     * 修改用户的虚拟币信息
     *
     * @param virtualCoin 虚拟币 - 数据对象
     * @return 修改结果
     */
    @PutMapping("/edit")
    public BaseResult<VirtualCoin> edit(@Validated({EditGroup.class}) @RequestBody VirtualCoin virtualCoin) {
        log.info("修改用户的虚拟币信息:{}", JSONUtil.toJsonPrettyStr(virtualCoin));
        return BaseResult.ok(virtualCoin);
    }

    /**
     * 修改用户的虚拟币扎状态
     *
     * @param virtualCoin 虚拟币 - 数据对象
     * @return 修改结果
     */
    @PutMapping("/edit/status")
    public BaseResult<VirtualCoin> editStatus(@Validated({EditStatusGroup.class}) @RequestBody VirtualCoin virtualCoin) {
        log.info("修改用户的虚拟币扎状态:{}", JSONUtil.toJsonPrettyStr(virtualCoin));
        return BaseResult.ok(virtualCoin);
    }

    /**
     * 批量删除用户虚拟币
     *
     * @param memberIds 用户id集合
     * @return 结果
     */
    @DeleteMapping("/delete/{memberIds}")
    public BaseResult<Void> delete(@PathVariable("memberIds") Long[] memberIds) {
        log.info("批量删除用户虚拟币:{}", JSONUtil.toJsonPrettyStr(memberIds));
        Map<Long, VirtualCoin> hashMap = Maps.newLinkedHashMap(COIN_MAP);
        List<Long> list = Lists.newArrayList(memberIds);
        for (Long memberId : list) {
            hashMap.remove(memberId);
        }
        return BaseResult.ok();
    }
}
