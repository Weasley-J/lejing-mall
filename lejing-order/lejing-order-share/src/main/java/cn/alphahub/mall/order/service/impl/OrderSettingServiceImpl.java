package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderSetting;
import cn.alphahub.mall.order.mapper.OrderSettingMapper;
import cn.alphahub.mall.order.service.OrderSettingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单配置信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
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
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<OrderSetting> wrapper = new QueryWrapper<>(orderSetting);
        // 2. 创建一个分页对象
        PageResult<OrderSetting> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<OrderSetting> orderSettingList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(orderSettingList);
    }

}
