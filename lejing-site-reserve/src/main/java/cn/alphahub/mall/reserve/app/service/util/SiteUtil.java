package cn.alphahub.mall.reserve.app.service.util;

import cn.alphahub.mall.reserve.app.pojo.bo.SiteBookBO;
import cn.alphahub.mall.reserve.app.pojo.bo.SiteDetailBO;
import cn.alphahub.mall.reserve.app.pojo.vo.SiteBookVO;
import cn.alphahub.mall.reserve.site.domain.SiteReserveDetail;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveDetailMapper;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveSessionMapper;
import cn.alphahub.mall.reserve.site.mapper.SiteReserveStatusMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * APP场地预约综合业处理务类
 *
 * @author liuwenjing
 */
@Slf4j
@Component
public class SiteUtil {

    @Autowired
    private SiteReserveStatusMapper siteReserveStatusMapper;
    @Autowired
    private SiteReserveDetailMapper siteReserveDetailMapper;
    @Autowired
    private SiteReserveSessionMapper siteReserveSessionMapper;

    /**
     * 根据siteId查当前时间往后七天的可预约场次数量，>= 1可预约
     * 当前时间起7天内的数据数量
     *
     * @param siteId site id
     * @param days   How many days of data do you want to get
     * @return count，>= 1可预约
     */
    public int getWeekReserveCount(Long siteId, Integer days) {
        return siteReserveStatusMapper.getDataCountInDays(siteId, days);
    }

    /**
     * 根据siteId获取场地信息详情
     *
     * @param siteId 场地id
     * @return 场地详情列表
     */
    public List<SiteDetailBO> getSiteDetails(Long siteId) {
        QueryWrapper<SiteReserveDetail> queryWrapper = new QueryWrapper<>(
                SiteReserveDetail.builder().siteId(siteId).build()
        );
        List<SiteReserveDetail> detail = siteReserveDetailMapper.selectList(queryWrapper);
        if (CollectionUtils.isEmpty(detail)) {
            return null;
        }
        return detail.stream().map(reserveDetail -> {
            SiteDetailBO detailBO = new SiteDetailBO();
            // ...
            detailBO.setSitePubDictName(reserveDetail.getSitePubDictCode().toString());
            detailBO.setSitePubTopic(reserveDetail.getSitePubTopic());
            return detailBO;
        }).collect(Collectors.toList());
    }


    /**
     * 根据siteId场地预定详情
     *
     * @param siteId 场地id
     * @return
     */
    public List<SiteBookBO> getSiteBookDetails(Long siteId) {
        List<SiteBookVO> sessionDataInDays = siteReserveStatusMapper.getLatestSessionDataInDays(siteId, 7);
        if (CollectionUtils.isEmpty(sessionDataInDays)) {
            return null;
        }

        return sessionDataInDays.stream().map(siteBookVO -> {
            SiteBookBO siteBookBO = new SiteBookBO();
            siteBookBO.setSiteSessionId(siteBookVO.getSiteSessionId());
            siteBookBO.setBookDate(siteBookVO.getEffectDate());
            siteBookBO.setLatestTime(siteBookVO.getSessionStartTime());
            return siteBookBO;
        }).collect(Collectors.toList());
    }

}
