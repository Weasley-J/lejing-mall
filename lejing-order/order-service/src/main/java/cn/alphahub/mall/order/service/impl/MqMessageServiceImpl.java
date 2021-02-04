package cn.alphahub.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.order.mapper.MqMessageMapper;
import cn.alphahub.mall.order.domain.MqMessage;
import cn.alphahub.mall.order.service.MqMessageService;

import java.util.List;

/**
 * MQ消息表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Service("mqMessageService")
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements MqMessageService {

    /**
     * 查询MQ消息表分页列表
     *
     * @param pageDomain   分页数据
     * @param mqMessage 分页对象
     * @return MQ消息表分页数据
     */
    @Override
    public PageResult<MqMessage> queryPage(PageDomain pageDomain, MqMessage mqMessage) {
        pageDomain.startPage();
        QueryWrapper<MqMessage> wrapper = new QueryWrapper<>(mqMessage);
        List<MqMessage> list = this.list(wrapper);
        PageInfo<MqMessage> pageInfo = new PageInfo<>(list);
        PageResult<MqMessage> pageResult = PageResult.<MqMessage>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}