package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderReturnApply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单退货申请Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderReturnApplyService extends IService<OrderReturnApply> {

    /**
     * 查询订单退货申请分页列表
     *
     * @param pageDomain       分页数据
     * @param orderReturnApply 分页对象
     * @return 订单退货申请分页数据
     */
    PageResult<OrderReturnApply> queryPage(PageDomain pageDomain, OrderReturnApply orderReturnApply);

}
