package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderSetting;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单配置信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderSettingService extends IService<OrderSetting> {

    /**
     * 查询订单配置信息分页列表
     *
     * @param pageDomain   分页数据
     * @param orderSetting 分页对象
     * @return 订单配置信息分页数据
     */
    PageResult<OrderSetting> queryPage(PageDomain pageDomain, OrderSetting orderSetting);

}
