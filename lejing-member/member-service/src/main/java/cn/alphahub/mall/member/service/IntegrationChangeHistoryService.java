package cn.alphahub.mall.member.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.member.entity.IntegrationChangeHistoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 积分变化历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:20:49
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

