package cn.alphahub.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.coupon.mapper.SeckillSkuNoticeMapper;
import cn.alphahub.mall.coupon.domain.SeckillSkuNotice;
import cn.alphahub.mall.coupon.service.SeckillSkuNoticeService;

import java.util.List;

/**
 * 秒杀商品通知订阅Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Service("seckillSkuNoticeService")
public class SeckillSkuNoticeServiceImpl extends ServiceImpl<SeckillSkuNoticeMapper, SeckillSkuNotice> implements SeckillSkuNoticeService {

    /**
     * 查询秒杀商品通知订阅分页列表
     *
     * @param pageDomain   分页数据
     * @param seckillSkuNotice 分页对象
     * @return 秒杀商品通知订阅分页数据
     */
    @Override
    public PageResult<SeckillSkuNotice> queryPage(PageDomain pageDomain, SeckillSkuNotice seckillSkuNotice) {
        pageDomain.startPage();
        QueryWrapper<SeckillSkuNotice> wrapper = new QueryWrapper<>(seckillSkuNotice);
        List<SeckillSkuNotice> list = this.list(wrapper);
        PageInfo<SeckillSkuNotice> pageInfo = new PageInfo<>(list);
        PageResult<SeckillSkuNotice> pageResult = PageResult.<SeckillSkuNotice>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}