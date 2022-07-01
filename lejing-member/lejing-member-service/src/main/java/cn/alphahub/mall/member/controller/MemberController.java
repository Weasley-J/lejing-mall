package cn.alphahub.mall.member.controller;

import cn.alphahub.common.annotations.Syslog;
import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.controller.BaseController;
import cn.alphahub.common.core.domain.Result;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enums.CheckUserExistsStatus;
import cn.alphahub.mall.auth.domain.SocialUser;
import cn.alphahub.mall.coupon.domain.Coupon;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.domain.MemberLevel;
import cn.alphahub.mall.member.feign.CouponClient;
import cn.alphahub.mall.member.service.MemberLevelService;
import cn.alphahub.mall.member.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 会员Controller
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Slf4j
@RestController
@RequestMapping("member/member")
public class MemberController extends BaseController {
    @Resource
    private MemberService memberService;
    @Resource
    private CouponClient couponClient;
    @Resource
    private MemberLevelService memberLevelService;

    /**
     * 查询会员列表
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param member      会员, 查询字段选择性传入, 默认为等值查询
     * @return 会员分页数据
     */
    @GetMapping("/list")
    public Result<PageResult<Member>> list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Member member
    ) {
        PageDomain pageDomain = new PageDomain(page, rows, orderColumn, isAsc);
        PageResult<Member> pageResult = memberService.queryPage(pageDomain, member);
        if (ObjectUtils.isNotEmpty(pageResult.getItems())) {
            return Result.ok(pageResult);
        }
        return Result.fail(HttpStatus.NOT_FOUND, "查询结果为空");
    }

    /**
     * 获取会员详情
     *
     * @param id 会员主键id
     * @return 会员详细信息
     */
    @GetMapping("/info/{id}")
    public Result<Member> info(@PathVariable("id") Long id) {
        Member member = memberService.getById(id);
        return ObjectUtils.anyNotNull(member) ? Result.ok(member) : Result.fail();
    }

    /**
     * 新增会员
     *
     * @param member 会员元数据
     * @return 成功返回true, 失败返回false
     */
    @PostMapping("/save")
    public Result<Boolean> save(@RequestBody Member member) {
        // 保存会员信息前先判断下是否已经注册过了
        CheckUserExistsStatus status = memberService.checkUserExistsStatus(member);
        if (CheckUserExistsStatus.USER_CAN_REGISTER.getValue().equals(status.getValue())) {
            QueryWrapper<MemberLevel> wrapper = new QueryWrapper<>();
            List<MemberLevel> memberLevels = memberLevelService.list(wrapper.lambda().eq(MemberLevel::getDefaultStatus, 1));
            // 设置默认会员等级
            if (CollectionUtils.isNotEmpty(memberLevels)) {
                member.setLevelId(memberLevels.get(0).getId());
            }
            // 设置创建时间
            member.setCreateTime(new Date());
            boolean save = memberService.save(member);
            return Result.ok(member.getUsername() + "注册成功", save);
        } else {
            return Result.fail(status.getValue(), status.getName(), false);
        }
    }

    /**
     * 修改会员
     *
     * @param member 会员, 根据id选择性更新
     * @return 成功返回true, 失败返回false
     */
    @PutMapping("/update")
    public Result<Boolean> update(@RequestBody Member member) {
        boolean update = memberService.updateById(member);
        return toOperationResult(update);
    }

    /**
     * 批量删除会员
     *
     * @param ids 会员id集合
     * @return 成功返回true, 失败返回false
     */
    @DeleteMapping("/delete/{ids}")
    public Result<Boolean> delete(@PathVariable Long[] ids) {
        boolean delete = memberService.removeByIds(Arrays.asList(ids));
        return toOperationResult(delete);
    }

    /**
     * 获取优惠券信息-测试feign远程调用
     *
     * @param couponId coupon id
     * @return 优惠券
     */
    @GetMapping("/coupon/{couponId}")
    public Coupon getMemberCoupon(@PathVariable("couponId") Long couponId) {
        Result<Coupon> info = couponClient.info(couponId);
        return ObjectUtils.isNotEmpty(info) ? doConvertType(info, Coupon.class) : null;
    }

    /**
     * 查询优惠券信息列表-测试feign远程调用
     *
     * @param page        当前页码,默认第1页
     * @param rows        显示行数,默认10条
     * @param orderColumn 排序排序字段,默认不排序
     * @param isAsc       排序方式,desc或者asc
     * @param coupon      优惠券信息,字段选择性传入,默认为等值查询
     * @return 优惠券信息分页数据
     */
    @PostMapping("/coupon/list")
    public Result<PageResult<Coupon>> getCouponList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "orderColumn", defaultValue = "") String orderColumn,
            @RequestParam(value = "isAsc", defaultValue = "") String isAsc,
            Coupon coupon
    ) {
        return couponClient.list(page, rows, orderColumn, isAsc, coupon);
    }

    /**
     * 用户登录
     *
     * @param member 用户信息
     * @return 用户信息
     */
    @Syslog(name = "用户登录")
    @PostMapping("login")
    public Result<Member> login(@RequestBody Member member) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        Member one;
        try {
            one = memberService.getOne(wrapper.lambda().eq(Member::getUsername, member.getUsername())
                    .or().eq(Member::getMobile, member.getMobile())
                    .or().eq(Member::getEmail, member.getEmail()));
        } catch (Exception e) {
            log.error("查询用户失败, 异常原因: {}\n", e.getLocalizedMessage(), e);
            return Result.error("查询用户失败, 异常原因: " + e.getLocalizedMessage());
        }
        if (Objects.isNull(one)) {
            return Result.error("用户[" + member.getUsername() + "]不存在");
        }
        return Result.success(one);
    }

    /**
     * 处理微博社交登录
     *
     * @param socialUser 微博社交用户实体
     * @return 用户信息
     */
    @Syslog(name = "用户微博社交登录")
    @PostMapping("/oauth2/login")
    public Result<Member> oauthLogin(@RequestBody SocialUser socialUser) {
        Member member = memberService.loginByWeibo(socialUser);
        return ObjectUtils.isNotEmpty(member) ? Result.ok(member) : Result.fail();
    }

    /**
     * 使用微信的accessToken登录注册用户
     *
     * @param accessTokenInfo 微信accessToken信息
     * @return 用户信息
     */
    @PostMapping(value = "/weixin/login")
    public Result<Member> loginWithWeChat(@RequestParam("accessTokenInfo") String accessTokenInfo) {
        Member member = memberService.loginWithWeChat(accessTokenInfo);
        return ObjectUtils.isNotEmpty(member) ? Result.ok(member) : Result.fail(
                CheckUserExistsStatus.USER_IS_EMPTY.getValue(),
                CheckUserExistsStatus.USER_IS_EMPTY.getName()
        );
    }
}
