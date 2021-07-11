package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.OrderReturnReason;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 退货原因Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface OrderReturnReasonService extends IService<OrderReturnReason> {

    /**
     * 查询退货原因分页列表
     *
     * @param pageDomain        分页数据
     * @param orderReturnReason 分页对象
     * @return 退货原因分页数据
     */
    PageResult<OrderReturnReason> queryPage(PageDomain pageDomain, OrderReturnReason orderReturnReason);

}
