package cn.alphahub.mall.order.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.MqMessage;
import cn.alphahub.mall.order.mapper.MqMessageMapper;
import cn.alphahub.mall.order.service.MqMessageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MQ消息表Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage> implements MqMessageService {

    /**
     * 查询MQ消息表分页列表
     *
     * @param pageDomain 分页数据
     * @param mqMessage  分页对象
     * @return MQ消息表分页数据
     */
    @Override
    public PageResult<MqMessage> queryPage(PageDomain pageDomain, MqMessage mqMessage) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MqMessage> wrapper = new QueryWrapper<>(mqMessage);
        // 2. 创建一个分页对象
        PageResult<MqMessage> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MqMessage> mqMessageList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(mqMessageList);
    }

}
