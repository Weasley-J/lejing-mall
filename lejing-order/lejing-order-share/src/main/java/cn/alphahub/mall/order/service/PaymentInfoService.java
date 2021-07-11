package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 支付信息表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface PaymentInfoService extends IService<PaymentInfo> {

    /**
     * 查询支付信息表分页列表
     *
     * @param pageDomain  分页数据
     * @param paymentInfo 分页对象
     * @return 支付信息表分页数据
     */
    PageResult<PaymentInfo> queryPage(PageDomain pageDomain, PaymentInfo paymentInfo);

}
