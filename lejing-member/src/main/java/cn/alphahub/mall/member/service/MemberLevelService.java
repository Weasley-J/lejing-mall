package cn.alphahub.mall.member.service;

import cn.alphahub.common.utils.PageUtils;
import cn.alphahub.mall.member.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 会员等级
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:38:07
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

