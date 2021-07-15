package cn.alphahub.mall.ware.service.impl;

import cn.alphahub.common.core.domain.BaseResult;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberReceiveAddress;
import cn.alphahub.mall.order.dto.vo.FareVo;
import cn.alphahub.mall.order.dto.vo.MemberAddressVo;
import cn.alphahub.mall.ware.domain.WareInfo;
import cn.alphahub.mall.ware.feign.MemberReceiveAddressClient;
import cn.alphahub.mall.ware.mapper.WareInfoMapper;
import cn.alphahub.mall.ware.service.WareInfoService;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 仓库信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Slf4j
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements WareInfoService {

    @Value(value = "${lejing.fee.postage}")
    private BigDecimal postage;

    @Resource
    private MemberReceiveAddressClient memberReceiveAddressClient;

    /**
     * 查询仓库信息分页列表
     *
     * @param pageDomain 分页数据
     * @param wareInfo   分页对象
     * @return 仓库信息分页数据
     */
    @Override
    public PageResult<WareInfo> queryPage(PageDomain pageDomain, WareInfo wareInfo) {
        QueryWrapper<WareInfo> wrapper = new QueryWrapper<>(wareInfo);
        PageResult<WareInfo> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<WareInfo> wareInfoList = this.list(wrapper);
        return pageResult.getPage(wareInfoList);
    }

    @Override
    public PageResult<WareInfo> queryPage(PageDomain pageDomain, WareInfo wareInfo, String key) {
        QueryWrapper<WareInfo> wrapper = new QueryWrapper<>(wareInfo);
        wrapper.lambda().and(StringUtils.isNotBlank(key), w -> w.eq(WareInfo::getId, key)
                .or().like(WareInfo::getName, key)
                .or().like(WareInfo::getAddress, key)
                .or().eq(WareInfo::getAreacode, key));
        PageResult<WareInfo> pageResult = new PageResult<>();
        pageResult.startPage(pageDomain);
        List<WareInfo> wareInfoList = this.list(wrapper);
        return pageResult.getPage(wareInfoList);
    }

    @Override
    public FareVo getPostageInfoByAddressId(Long addressId) {
        FareVo fare = new FareVo(null, BigDecimal.ZERO);
        BaseResult<MemberReceiveAddress> result = memberReceiveAddressClient.info(addressId);
        log.info("远程查询收货地址:{}", JSONUtil.toJsonStr(result));
        if (result.getSuccess() && Objects.nonNull(result.getData())) {
            MemberReceiveAddress receiveAddress = result.getData();
            String phone = receiveAddress.getPhone();
            //TODO 调用第三那方接口查询邮资
            BigDecimal postFee = Objects.nonNull(postage) ? postage : new BigDecimal(StringUtils.substring(phone, phone.length() - 1, phone.length()));
            MemberAddressVo addressVo = new MemberAddressVo();
            BeanUtils.copyProperties(receiveAddress, addressVo);
            fare.setFare(postFee);
            fare.setAddress(addressVo);
        }
        return fare;
    }
}
