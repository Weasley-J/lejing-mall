package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.SpuBounds;
import cn.alphahub.mall.coupon.mapper.SpuBoundsMapper;
import cn.alphahub.mall.coupon.service.SpuBoundsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品spu积分设置Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class SpuBoundsServiceImpl extends ServiceImpl<SpuBoundsMapper, SpuBounds> implements SpuBoundsService {

    /**
     * 查询商品spu积分设置分页列表
     *
     * @param pageDomain 分页数据
     * @param spuBounds  分页对象
     * @return 商品spu积分设置分页数据
     */
    @Override
    public PageResult<SpuBounds> queryPage(PageDomain pageDomain, SpuBounds spuBounds) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<SpuBounds> wrapper = new QueryWrapper<>(spuBounds);
        // 2. 创建一个分页对象
        PageResult<SpuBounds> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<SpuBounds> spuBoundsList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(spuBoundsList);
    }

}
