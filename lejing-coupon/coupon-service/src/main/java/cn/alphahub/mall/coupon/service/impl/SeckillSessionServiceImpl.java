package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SeckillSessionMapper;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.service.SeckillSessionService;

import java.util.List;

/**
 * 秒杀活动场次Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Service("seckillSessionService")
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {

    /**
     * 查询秒杀活动场次分页列表
     *
     * @param pageDomain   分页数据
     * @param seckillSession 分页对象
     * @return 秒杀活动场次分页数据
     */
    @Override
    public PageResult<SeckillSession> queryPage(PageDomain pageDomain, SeckillSession seckillSession) {
        pageDomain.startPage();
        QueryWrapper<SeckillSession> wrapper = new QueryWrapper<>(seckillSession);
        List<SeckillSession> list = this.list(wrapper);
        PageInfo<SeckillSession> pageInfo = new PageInfo<>(list);
        PageResult<SeckillSession> pageResult = PageResult.<SeckillSession>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}