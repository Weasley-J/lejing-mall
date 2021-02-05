package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.RefundInfoMapper;
import cn.alphahub.mall.order.domain.RefundInfo;
import cn.alphahub.mall.order.service.RefundInfoService;

import java.util.List;

/**
 * 退款信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:43:32
 */
@Service("refundInfoService")
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    /**
     * 查询退款信息分页列表
     *
     * @param pageDomain   分页数据
     * @param refundInfo 分页对象
     * @return 退款信息分页数据
     */
    @Override
    public PageResult<RefundInfo> queryPage(PageDomain pageDomain, RefundInfo refundInfo) {
        pageDomain.startPage();
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>(refundInfo);
        List<RefundInfo> list = this.list(wrapper);
        PageInfo<RefundInfo> pageInfo = new PageInfo<>(list);
        PageResult<RefundInfo> pageResult = PageResult.<RefundInfo>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}