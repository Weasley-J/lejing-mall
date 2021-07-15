package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSession;
import cn.alphahub.mall.coupon.mapper.SeckillSessionMapper;
import cn.alphahub.mall.coupon.service.SeckillSessionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀活动场次Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {

    /**
     * 查询秒杀活动场次分页列表
     *
     * @param pageDomain     分页数据
     * @param seckillSession 分页对象
     * @return 秒杀活动场次分页数据
     */
    @Override
    public PageResult<SeckillSession> queryPage(PageDomain pageDomain, SeckillSession seckillSession) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SeckillSession> wrapper = new QueryWrapper<>(seckillSession);
        // 2. 创建一个分页对象
        PageResult<SeckillSession> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SeckillSession> seckillSessionList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(seckillSessionList);
    }

}
