package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 退款信息Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface RefundInfoService extends IService<RefundInfo> {

    /**
     * 查询退款信息分页列表
     *
     * @param pageDomain 分页数据
     * @param refundInfo 分页对象
     * @return 退款信息分页数据
     */
    PageResult<RefundInfo> queryPage(PageDomain pageDomain, RefundInfo refundInfo);

}
