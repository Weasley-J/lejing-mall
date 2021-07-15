package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SeckillSkuNotice;
import cn.alphahub.mall.coupon.mapper.SeckillSkuNoticeMapper;
import cn.alphahub.mall.coupon.service.SeckillSkuNoticeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 秒杀商品通知订阅Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SeckillSkuNoticeServiceImpl extends ServiceImpl<SeckillSkuNoticeMapper, SeckillSkuNotice> implements SeckillSkuNoticeService {

    /**
     * 查询秒杀商品通知订阅分页列表
     *
     * @param pageDomain       分页数据
     * @param seckillSkuNotice 分页对象
     * @return 秒杀商品通知订阅分页数据
     */
    @Override
    public PageResult<SeckillSkuNotice> queryPage(PageDomain pageDomain, SeckillSkuNotice seckillSkuNotice) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SeckillSkuNotice> wrapper = new QueryWrapper<>(seckillSkuNotice);
        // 2. 创建一个分页对象
        PageResult<SeckillSkuNotice> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SeckillSkuNotice> seckillSkuNoticeList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(seckillSkuNoticeList);
    }

}
