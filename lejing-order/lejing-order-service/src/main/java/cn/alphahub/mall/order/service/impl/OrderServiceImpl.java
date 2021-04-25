package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.to.UserInfoTo;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.order.domain.Order;
import cn.alphahub.mall.order.dto.vo.MemberAddressVo;
import cn.alphahub.mall.order.dto.vo.OrderConfirmVo;
import cn.alphahub.mall.order.feign.MemberReceiveAddressClient;
import cn.alphahub.mall.order.interceptor.LoginInterceptor;
import cn.alphahub.mall.order.mapper.OrderMapper;
import cn.alphahub.mall.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service业务层处理
 *
 * @author Weasley J
 * @date 2021-02-24 16:02:31
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Resource
    private MemberReceiveAddressClient memberReceiveAddressClient;

    /**
     * 查询订单分页列表
     *
     * @param pageDomain 分页数据
     * @param order      分页对象
     * @return 订单分页数据
     */
    @Override
    public PageResult<Order> queryPage(PageDomain pageDomain, Order order) {
        PageResult<Order> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<Order> orderList = list(new QueryWrapper<>(order));
        return pageResult.getPage(orderList);
    }

    /**
     * 去结算确认页
     */
    @Override
    public OrderConfirmVo confirmOrder() {
        OrderConfirmVo vo = OrderConfirmVo.builder().build();

        //从拦截器中获取当前用户信息
        UserInfoTo userInfo = LoginInterceptor.getUserInfo();

        BaseResult<List<MemberReceiveAddress>> result = memberReceiveAddressClient.memberAddressList(userInfo.getUserId());
        if (result.getSuccess() && CollectionUtils.isNotEmpty(result.getData())) {
            List<MemberAddressVo> vos = result.getData().stream().map(memberReceiveAddress -> {
                MemberAddressVo addressVo = MemberAddressVo.builder().build();
                BeanUtils.copyProperties(memberReceiveAddress, addressVo);
                return addressVo;
            }).collect(Collectors.toList());
            vo.setMemberAddressVos(vos);
        }
        return vo;
    }

}
