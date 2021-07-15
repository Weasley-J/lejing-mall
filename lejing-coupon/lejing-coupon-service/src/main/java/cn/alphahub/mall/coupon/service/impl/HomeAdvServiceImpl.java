package cn.alphahub.mall.coupon.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.coupon.domain.HomeAdv;
import cn.alphahub.mall.coupon.mapper.HomeAdvMapper;
import cn.alphahub.mall.coupon.service.HomeAdvService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页轮播广告Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:31:15
 */
@Service
public class HomeAdvServiceImpl extends ServiceImpl<HomeAdvMapper, HomeAdv> implements HomeAdvService {

    /**
     * 查询首页轮播广告分页列表
     *
     * @param pageDomain 分页数据
     * @param homeAdv    分页对象
     * @return 首页轮播广告分页数据
     */
    @Override
    public PageResult<HomeAdv> queryPage(PageDomain pageDomain, HomeAdv homeAdv) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<HomeAdv> wrapper = new QueryWrapper<>(homeAdv);
        // 2. 创建一个分页对象
        PageResult<HomeAdv> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<HomeAdv> homeAdvList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(homeAdvList);
    }

}
