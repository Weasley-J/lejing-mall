package cn.alphahub.mall.order.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.order.domain.MqMessage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * MQ消息表Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:02:31
 */
public interface MqMessageService extends IService<MqMessage> {

    /**
     * 查询MQ消息表分页列表
     *
     * @param pageDomain 分页数据
     * @param mqMessage  分页对象
     * @return MQ消息表分页数据
     */
    PageResult<MqMessage> queryPage(PageDomain pageDomain, MqMessage mqMessage);

}
