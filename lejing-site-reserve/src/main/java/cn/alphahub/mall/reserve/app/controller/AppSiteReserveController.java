package cn.alphahub.mall.reserve.app.controller;


import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.common.core.domain.Result;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteCouponBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteSessionBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteSessionOrderBO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderDetailVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderReimburseVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteReserveDetailVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteReserveVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteSessionVO;
import cn.alphahub.mall.reserve.app.service.AppSiteReserveService;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * App场地预约Controller
 *
 * @author liuwenjing
 * @date 2021年2月16日
 */
@Slf4j
@RestController
@RequestMapping("site/app/")
public class AppSiteReserveController {

    @Autowired
    private AppSiteReserveService siteReserveService;

    /**
     * 预约场地-分页（当前往后推7日）
     *
     * @param page      当前页码,默认第一页
     * @param rows      本页条数，默认每页显示10条
     * @param projectId 项目id
     * @return 分页结果集
     */
    @GetMapping("reserveList")
    public Result<PageResult<SiteReserveVO>> reserveList(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam(value = "projectId", defaultValue = "LJ1000") String projectId
    ) throws Exception {
        SiteReserveVO reserveVO = SiteReserveVO.builder().projectId(projectId).build();
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPage(page);
        pageDomain.setRows(rows);
        PageResult<SiteReserveVO> pageResult = siteReserveService.queryPage(pageDomain, reserveVO);
        if (Objects.nonNull(pageResult)) {
            return Result.ok("查询成功", pageResult);
        }
        return Result.fail("查询失败");
    }

    /**
     * 获取XX场地的7日内预约详情
     *
     * @param siteId 场地id
     * @return 7日内预约详情
     */
    @GetMapping("siteBookDetail/{siteId}")
    public Result<SiteReserveDetailVO> siteBookDetail(@PathVariable("siteId") Long siteId) {
        SiteReserveDetailVO detailVO = siteReserveService.siteBookDetail(siteId);
        return Result.ok(detailVO);
    }

    /**
     * 获取场地yyyy-MM-dd号的场次信息
     *
     * @param siteId     场地id
     * @param effectDate 要查询的日期, 格式yyyy-MM-dd
     * @return 指定日期可用场次信息
     */
    @GetMapping("siteSessions")
    public Result<List<SiteSessionVO>> siteSessions(
            @RequestParam("siteId") Long siteId,
            @RequestParam("effectDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date effectDate
    ) {
        log.info("场地id:{},查询日期:{}", siteId, effectDate);
        String dateTimeNow = DateUtil.format(new Date(), "yyyy-MM-dd");
        effectDate = Objects.isNull(effectDate) ? DateUtil.parseDate(dateTimeNow) : effectDate;

        List<SiteSessionVO> siteSessions = siteReserveService.siteSessions(siteId, effectDate);

        if (CollectionUtils.isEmpty(siteSessions)) {
            return Result.fail();
        }
        return Result.ok(siteSessions);
    }

    /**
     * 确认订单提交预约
     *
     * @param sessionOrder 提交预约的元数据
     * @return 支付结果
     */
    @PostMapping("confirmOrder")
    public Result<SiteSessionOrderBO> confirmOrder(@RequestBody SiteSessionOrderBO sessionOrder) {
        if (ObjectUtils.isNotEmpty(sessionOrder)) {
            log.info("用户id: {}, 用户手机号码: {}, 订单总价: {}, 用户备注: {}",
                    sessionOrder.getUserId(), sessionOrder.getUserPhone(),
                    sessionOrder.getTotalPrice(), sessionOrder.getRemark());
            System.out.println("------------------------");
            List<SiteSessionBO> sessionBOList = sessionOrder.getSessions();
            if (!CollectionUtils.isEmpty(sessionBOList)) {
                sessionBOList.forEach(sessionVO -> log.info("预约场次是：{}", sessionVO));
            }
        }
        Boolean paid = siteReserveService.confirmOrder(sessionOrder);
        if (paid) {
            return Result.ok("支付成功", sessionOrder);
        }
        return Result.fail("支付失败", sessionOrder);
    }

    /**
     * 场地预约订单列表-分页
     *
     * @param page        当前页码,默认第一页
     * @param rows        本页条数，默认每页显示10条
     * @param userId      用户id
     * @param orderStatus 订单状态码/券的使用状态码
     * @return 订单信息分页列表
     */
    @GetMapping("siteOrderStatus")
    public Result<PageResult<SiteOrderVO>> siteOrderStatus(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "10") Integer rows,
            @RequestParam("userId") String userId,
            @RequestParam("orderStatus") String orderStatus
    ) {
        PageResult<SiteOrderVO> result = this.siteReserveService.siteOrderStatus(page, rows, userId, orderStatus);
        return Result.ok(result);
    }

    /**
     * 查询订单详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @GetMapping("siteOrderDetail")
    public Result<SiteOrderDetailVO> siteOrderDetail(@RequestParam("orderMasterId") String orderMasterId) {
        SiteOrderDetailVO detailVO = this.siteReserveService.siteOrderDetail(orderMasterId);
        return Result.ok(detailVO);
    }

    /**
     * 查看入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    @GetMapping("lookOverCoupon")
    public Result<SiteCouponBO> lookOverCoupon(@RequestParam("couponCode") String couponCode) {
        SiteCouponBO couponBO = this.siteReserveService.lookOverCoupon(couponCode);
        return Result.ok(couponBO);
    }

    /**
     * 下载入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    @GetMapping("downloadCoupon")
    public Result<Object> downloadCoupon(@RequestParam("couponCode") String couponCode) {
        // TODO
        return Result.ok("你下了个寂寞", "http://img.zz21.com/2015/0419/20150419084506614.jpg");
    }

    /**
     * 申请退款
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @GetMapping("requestRefund")
    public Result<Boolean> requestRefund(@RequestParam("orderMasterId") String orderMasterId) {
        Boolean requestFlag = siteReserveService.requestRefund(orderMasterId);
        if (requestFlag) {
            return Result.ok("申请成功", true);
        }
        return Result.fail("申请失败", true);
    }

    /**
     * 退款详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @GetMapping("refundDetail")
    public Result<SiteOrderReimburseVO> refundDetail(@RequestParam("orderMasterId") String orderMasterId) {
        SiteOrderReimburseVO reimburseVO = siteReserveService.refundDetail(orderMasterId);
        return Result.ok(reimburseVO);
    }
}
