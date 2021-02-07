package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.PaymentInfoMapper;
import cn.alphahub.mall.order.domain.PaymentInfo;
import cn.alphahub.mall.order.service.PaymentInfoService;

import java.util.List;

/**
 * 支付信息表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:45:12
 */
@Service("paymentInfoService")
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    /**
     * 查询支付信息表分页列表
     *
     * @param pageDomain   分页数据
     * @param paymentInfo 分页对象
     * @return 支付信息表分页数据
     */
    @Override
    public PageResult<PaymentInfo> queryPage(PageDomain pageDomain, PaymentInfo paymentInfo) {
        pageDomain.startPage();
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>(paymentInfo);
        List<PaymentInfo> list = this.list(wrapper);
        PageInfo<PaymentInfo> pageInfo = new PageInfo<>(list);
        PageResult<PaymentInfo> pageResult = PageResult.<PaymentInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}