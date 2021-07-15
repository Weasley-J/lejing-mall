package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.RefundInfo;
import cn.alphahub.mall.order.mapper.RefundInfoMapper;
import cn.alphahub.mall.order.service.RefundInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退款信息Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {

    /**
     * 查询退款信息分页列表
     *
     * @param pageDomain 分页数据
     * @param refundInfo 分页对象
     * @return 退款信息分页数据
     */
    @Override
    public PageResult<RefundInfo> queryPage(PageDomain pageDomain, RefundInfo refundInfo) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<RefundInfo> wrapper = new QueryWrapper<>(refundInfo);
        // 2. 创建一个分页对象
        PageResult<RefundInfo> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<RefundInfo> refundInfoList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(refundInfoList);
    }

}
