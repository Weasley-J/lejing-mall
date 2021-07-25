package cn.alphahub.mall.coupon.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 秒杀活动场次Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
public interface SeckillSessionService extends IService<SeckillSession> {

    /**
     * 查询秒杀活动场次分页列表
     *
     * @param pageDomain     分页数据
     * @param seckillSession 分页对象
     * @return 秒杀活动场次分页数据
     */
    PageResult<SeckillSession> queryPage(PageDomain pageDomain, SeckillSession seckillSession);

    /**
     * 获取最近3天的秒杀场次列表
     *
     * @return 最近3天的秒杀场次列表
     */
    List<SeckillSession> getLatest3DaysSeckillSession();

}
