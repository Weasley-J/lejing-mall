package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.PaymentInfo;
import cn.alphahub.mall.order.mapper.PaymentInfoMapper;
import cn.alphahub.mall.order.service.PaymentInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付信息表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    /**
     * 查询支付信息表分页列表
     *
     * @param pageDomain  分页数据
     * @param paymentInfo 分页对象
     * @return 支付信息表分页数据
     */
    @Override
    public PageResult<PaymentInfo> queryPage(PageDomain pageDomain, PaymentInfo paymentInfo) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>(paymentInfo);
        // 2. 创建一个分页对象
        PageResult<PaymentInfo> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<PaymentInfo> paymentInfoList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(paymentInfoList);
    }

}
