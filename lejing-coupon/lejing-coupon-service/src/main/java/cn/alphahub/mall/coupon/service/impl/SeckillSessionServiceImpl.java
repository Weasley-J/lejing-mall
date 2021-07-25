package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.domain.SeckillSkuRelation;
import cn.alphahub.mall.coupon.mapper.SeckillSessionMapper;
import cn.alphahub.mall.coupon.service.SeckillSessionService;
import cn.alphahub.mall.coupon.service.SeckillSkuRelationService;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 秒杀活动场次Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {
    @Resource
    private SeckillSkuRelationService seckillSkuRelationService;

    /**
     * 查询秒杀活动场次分页列表
     *
     * @param pageDomain     分页数据
     * @param seckillSession 分页对象
     * @return 秒杀活动场次分页数据
     */
    @Override
    public PageResult<SeckillSession> queryPage(PageDomain pageDomain, SeckillSession seckillSession) {
        PageResult<SeckillSession> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);

        QueryWrapper<SeckillSession> wrapper = new QueryWrapper<>(seckillSession);
        List<SeckillSession> seckillSessionList = this.list(wrapper);

        return pageResult.getPage(seckillSessionList);
    }

    @Override
    public List<SeckillSession> getLatest3DaysSeckillSession() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime offset = LocalDateTimeUtil.offset(now, 3, ChronoUnit.DAYS);

        LocalDateTime start = LocalDateTime.of(LocalDate.from(now), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.from(offset), LocalTime.MAX);

        String startTime = LocalDateTimeUtil.format(start, "yyyy-MM-dd HH:mm:ss");
        String endTime = LocalDateTimeUtil.format(end, "yyyy-MM-dd HH:mm:ss");

        System.err.println("当前时间：" + startTime + ", 3天后：" + endTime);

        List<SeckillSession> seckillSessions = list(new QueryWrapper<SeckillSession>().lambda().between(SeckillSession::getStartTime, startTime, endTime));
        if (CollectionUtils.isNotEmpty(seckillSessions)) {
            seckillSessions = seckillSessions.stream().peek(seckillSession -> {
                List<SeckillSkuRelation> skuRelations = seckillSkuRelationService.list(new QueryWrapper<SeckillSkuRelation>().lambda()
                        .eq(SeckillSkuRelation::getPromotionSessionId, seckillSession.getId())
                );
                seckillSession.setSkuRelations(skuRelations);
            }).collect(Collectors.toList());
        }
        return seckillSessions;
    }
}
