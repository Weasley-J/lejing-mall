package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.OrderSettingMapper;
import cn.alphahub.mall.order.domain.OrderSetting;
import cn.alphahub.mall.order.service.OrderSettingService;

import java.util.List;

/**
 * 订单配置信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("orderSettingService")
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements OrderSettingService {

    /**
     * 查询订单配置信息分页列表
     *
     * @param pageDomain   分页数据
     * @param orderSetting 分页对象
     * @return 订单配置信息分页数据
     */
    @Override
    public PageResult<OrderSetting> queryPage(PageDomain pageDomain, OrderSetting orderSetting) {
        pageDomain.startPage();
        QueryWrapper<OrderSetting> wrapper = new QueryWrapper<>(orderSetting);
        List<OrderSetting> list = this.list(wrapper);
        PageInfo<OrderSetting> pageInfo = new PageInfo<>(list);
        PageResult<OrderSetting> pageResult = PageResult.<OrderSetting>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}