package cn.alphahub.mall.reserve.app.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteCouponBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteDetailBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteReserveDateBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteSessionOrderBO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderDetailVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderReimburseVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteOrderVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteReserveDetailVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteReserveVO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteSessionVO;
import cn.alphahub.mall.reserve.app.service.AppSiteReserveService;
import cn.alphahub.mall.reserve.app.service.util.SiteUtil;
import cn.alphahub.mall.reserve.site.domain.SiteReserve;
import cn.alphahub.mall.reserve.site.service.SiteReserveService;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 场地预约App Service业务层处理
 *
 * @author liuwenjing
 * @date 2021年2月16日
 */
@Service
public class AppSiteReserveServiceImpl implements AppSiteReserveService {
    protected static final String SITE_NAME = "乐璟生活社区足球场";
    @Autowired
    private SiteUtil siteUtil;
    @Autowired
    private SiteReserveService siteReserveService;

    /**
     * 查询分页列表
     *
     * @param pageDomain 分页数据
     * @param domain     分页对象
     * @return 分页集合
     */
    @Override
    public PageResult<SiteReserveVO> queryPage(PageDomain pageDomain, SiteReserveVO domain) {

        pageDomain.startPage();
        SiteReserve reserve = SiteReserve.builder().projectCode(domain.getProjectId()).build();

        List<SiteReserve> list = siteReserveService.list(new QueryWrapper<>(reserve));

        List<SiteReserveVO> reserveVOList = list.stream().map(ebSiteReserve -> {
            SiteReserveVO reserveVO = SiteReserveVO.builder().build();
            BeanUtils.copyProperties(ebSiteReserve, reserveVO);
            // 7日内预约情况，用场地id（siteId）查当前时间至未来七天的可预约场次数量，weekReserveCount >= 1 可预约
            int weekReserveCount = siteUtil.getWeekReserveCount(ebSiteReserve.getSiteId(), 7);
            reserveVO.setWeekReserveCount(weekReserveCount);
            reserveVO.setImageUrl(Arrays.asList(ebSiteReserve.getImageUrl().split(",")));
            return reserveVO;
        }).collect(Collectors.toList());

        PageInfo<SiteReserveVO> pageInfo = new PageInfo<>(reserveVOList);

        var pageResult = new PageResult<SiteReserveVO>();
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setItems(pageInfo.getList());

        return pageResult;
    }

    /**
     * 获取XX场地的7日内预约详情
     *
     * @param siteId 场地id
     * @return 7日内预约详情
     */
    @Override
    public SiteReserveDetailVO siteBookDetail(Long siteId) {

        SiteReserveDetailVO detailVO = new SiteReserveDetailVO();
        // 查主表
        SiteReserve ebSiteReserve = siteReserveService.getById(siteId);
        // 预约量
        int weekReserveCount = siteUtil.getWeekReserveCount(ebSiteReserve.getSiteId(), 0);
        BeanUtils.copyProperties(ebSiteReserve, detailVO);
        detailVO.setImageUrl(Arrays.asList(ebSiteReserve.getImageUrl().split(",")));
        detailVO.setWeekReserveCount(weekReserveCount);

        //场地预定详情
        detailVO.setSiteBookList(siteUtil.getSiteBookDetails((siteId)));

        //场地信息详情
        detailVO.setSiteDetailList(siteUtil.getSiteDetails(siteId));

        return detailVO;
    }

    /**
     * 获取场地yyyy-MM-dd号的场次信息
     *
     * @param siteId     场地id
     * @param effectDate 要查询的日期, 格式yyyy-MM-dd
     * @return 指定日期可用场次信息
     */
    @Override
    public List<SiteSessionVO> siteSessions(Long siteId, Date effectDate) {
        List<SiteSessionVO> siteSessions = new ArrayList<>();

        // TODO 业务
        for (long i = 1; i <= 8; i++) {
            SiteSessionVO sessionVO = new SiteSessionVO();
            sessionVO.setSiteSessionId(i);
            sessionVO.setSiteId(i + 1549489L);
            sessionVO.setSiteTitle(SITE_NAME);
            sessionVO.setEffectDate(effectDate);
            sessionVO.setSessionStartTime("1" + i + ":00");
            sessionVO.setSessionFinishTime("1" + (i + 1) + ":00");
            sessionVO.setCurrentPrice((int) i + 5200);
            sessionVO.setSessionStatus(i % 2 == 0 ? 0 : 2);
            sessionVO.setStatusName(sessionVO.getSessionStatus());
            sessionVO.setDeleted(false);
            siteSessions.add(sessionVO);
        }

        return siteSessions;
    }

    /**
     * 确认订单提交预约
     *
     * @param sessionOrder 提交预约的元数据
     * @return 支付结果
     */
    @Override
    public Boolean confirmOrder(SiteSessionOrderBO sessionOrder) {

        // TODO 合并订单sessionList

        // TODO 1. 预检查逻辑

        // TODO 2. 创建订单，插入多张数据表

        // TODO 3. 调用支付接口，判断支付回调结果

        int nextInt = RandomUtil.randomInt();

        boolean paid = nextInt % 2 == 0;

        // TODO 4.1 已支付逻辑
        if (paid) {
            // TODO 修改相关表状态-支付成功
            // ....
        }

        // TODO 4.2 未支付逻辑-支付失败
        // ....

        return paid;
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
    @Override
    public PageResult<SiteOrderVO> siteOrderStatus(
            Integer page,
            Integer rows,
            String userId,
            String orderStatus
    ) {

        // TODO 业务
        List<SiteOrderVO> orderVos = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            SiteOrderVO orderVO = SiteOrderVO.builder().build();
            orderVO.setUserId("89479128343" + i);
            orderVO.setSiteId(i + 93489315654L);
            orderVO.setSiteTitle(SITE_NAME);
            orderVO.setEffectDate(new Date());
            orderVO.setSessionStartTime("12:00");
            orderVO.setSessionFinishTime("18:00");
            orderVO.setTotalPrice(10010);
            orderVos.add(orderVO);
        }

        PageResult<SiteOrderVO> pageResult = new PageResult<>();
        pageResult.setTotalCount(3L);
        pageResult.setTotalPage(1);
        pageResult.setItems(orderVos);

        return pageResult;
    }

    /**
     * 查询订单详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @Override
    public SiteOrderDetailVO siteOrderDetail(String orderMasterId) {
        // TODO 查场次日期
        ArrayList<SiteReserveDateBO> dateList = Lists.newArrayList();
        // TODO 查场地详情
        ArrayList<SiteDetailBO> SiteDetails = Lists.newArrayList();


        for (int i = 1; i <= 3; i++) {
            SiteReserveDateBO dateVO = SiteReserveDateBO.builder().build();
            dateVO.setEffectDate(new Date());
            dateVO.setSiteOpenTime("1" + i + ":00");
            dateVO.setSiteCloseTime("1" + (i + 3) + ":00");
            dateVO.setCurrentPrice(1688 + i);
            dateList.add(dateVO);

            SiteDetailBO detailBO = SiteDetailBO.builder().build();
            detailBO.setSitePubDictCode(i);
            detailBO.setSitePubDictName("备注名称" + i);
            detailBO.setSitePubTopic("备注名称对应的场地信息" + i);
            SiteDetails.add(detailBO);
        }

        // TODO 订单详情-VO
        SiteOrderDetailVO detailVO = SiteOrderDetailVO.builder().build();
        detailVO.setSiteId(123456789L);
        detailVO.setSiteTitle(SITE_NAME);
        detailVO.setCouponCode("LJ-123456");
        detailVO.setCouponStatus(1);
        detailVO.setOrderMasterId(orderMasterId);
        detailVO.setUserPhone("10086");
        detailVO.setCreateTime(new Date());
        detailVO.setOrderActAmount(1688);
        detailVO.setReserveDateList(dateList);
        detailVO.setSiteDetailList(SiteDetails);

        return detailVO;
    }

    /**
     * 查看入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    @Override
    public SiteCouponBO lookOverCoupon(String couponCode) {

        // TODO 查预约时间列表
        // TODO 查场次日期
        ArrayList<SiteReserveDateBO> dateList = Lists.newArrayList();

        for (int i = 1; i <= 3; i++) {
            SiteReserveDateBO dateVO = new SiteReserveDateBO();
            dateVO.setEffectDate(new Date());
            dateVO.setSiteOpenTime("1" + i + ":00");
            dateVO.setSiteCloseTime("1" + (i + 3) + ":00");
            dateVO.setCurrentPrice(1688 + i);
            dateList.add(dateVO);
        }

        // TODO 组装BO对象
        SiteCouponBO couponBO = SiteCouponBO.builder().orderMasterId("LJ123432454354")
                .siteId(23789234L)
                .couponCode("aabb1234")
                .siteReserveDates(dateList)
                .deleted(false)
                .build();

        return couponBO;
    }

    /**
     * 下载入场券
     *
     * @param couponCode 优惠券码
     * @return
     */
    @Override
    public Object downloadCoupon(String couponCode) {
        return null;
    }

    /**
     * 申请退款
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @Override
    public Boolean requestRefund(String orderMasterId) {
        // TODO 1. 判断是否符合退款条件

        // TODO 2. 获取制支付方式

        // TODO 3. 回填eb_order_reimburse表

        // TODO 4. 更新退款状态

        // TODO 5. 整理并返回结果

        // TODO 返回Boolean值

        return RandomUtils.nextInt() % 2 == 0;
    }

    /**
     * 退款详情
     *
     * @param orderMasterId 主订单号
     * @return
     */
    @Override
    public SiteOrderReimburseVO refundDetail(String orderMasterId) {
        // TODO
        SiteOrderReimburseVO reimburseVO = new SiteOrderReimburseVO();
        reimburseVO.setReimburseId(1234567898998L);
        reimburseVO.setOrderMasterId("LJ202102081114");
        reimburseVO.setUserId(orderMasterId);
        reimburseVO.setProductTitle("乐璟生活社区电子入场券");
        reimburseVO.setActualPrice(1);
        reimburseVO.setReceiveAccount("返回至原支付账户");
        reimburseVO.setReimburseStatus(1);
        reimburseVO.setReimburseApplyDate(new Date());
        reimburseVO.setMerchantProcessDate(new Date());
        reimburseVO.setRefundSuccessfulDate(new Date());

        return reimburseVO;
    }
}
