package cn.alphahub.mall.app.service;


import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.app.pojo.bo.SiteCouponBO;
import cn.alphahub.mall.app.pojo.bo.SiteSessionOrderBO;
import cn.alphahub.mall.app.pojo.vo.SiteOrderDetailVO;
import cn.alphahub.mall.app.pojo.vo.SiteOrderReimburseVO;
import cn.alphahub.mall.app.pojo.vo.SiteOrderVO;
import cn.alphahub.mall.app.pojo.vo.SiteReserveDetailVO;
import cn.alphahub.mall.app.pojo.vo.SiteReserveVO;
import cn.alphahub.mall.app.pojo.vo.SiteSessionVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * 场地预约App Service业务层处理
 *
 * @author liuwenjing
 * @date 2021年2月16日
 */
public interface AppSiteReserveService extends PageService<SiteReserveVO> {
    /**
     * 获取XX场地的7日内预约详情
     *
     * @param siteId 场地id
     * @return 7日内预约详情
     */
    SiteReserveDetailVO siteBookDetail(Long siteId);

    /**
     * 获取场地yyyy-MM-dd号的场次信息
     *
     * @param siteId     场地id
     * @param effectDate 要查询的日期, 格式yyyy-MM-dd
     * @return 指定日期可用场次信息
     */
    List<SiteSessionVO> siteSessions(Long siteId, Date effectDate);

    /**
     * 确认订单提交预约
     *
     * @param sessionOrder 提交预约的元数据
     * @return 支付结果
     */
    Boolean confirmOrder(@RequestBody SiteSessionOrderBO sessionOrder);

    /**
     * 场地预约订单列表-分页
     *
     * @param page        当前页码,默认第一页
     * @param rows        本页条数，默认每页显示10条
     * @param userId      用户id
     * @param orderStatus 订单状态码/券的使用状态码
     * @return 订单信息分页列表
     */
    PageResult<SiteOrderVO> siteOrderStatus(Integer page, Integer rows, String userId, String orderStatus);

    /**
     * 查询订单详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    SiteOrderDetailVO siteOrderDetail(String orderMasterId);

    /**
     * 查看入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    SiteCouponBO lookOverCoupon(String couponCode);

    /**
     * 下载入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    Object downloadCoupon(String couponCode);

    /**
     * 申请退款
     *
     * @param orderMasterId 主订单号
     * @return
     */
    Boolean requestRefund(String orderMasterId);

    /**
     * 退款详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    SiteOrderReimburseVO refundDetail(String orderMasterId);
}
