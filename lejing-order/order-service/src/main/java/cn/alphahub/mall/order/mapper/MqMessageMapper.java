package cn.alphahub.mall.order.mapper;

import cn.alphahub.mall.order.domain.MqMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * MQ消息表
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:17:51
 */
@Mapper
public interface MqMessageMapper extends BaseMapper<MqMessage> {
	
}