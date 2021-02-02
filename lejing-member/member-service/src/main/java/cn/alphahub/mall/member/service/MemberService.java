package cn.alphahub.mall.member.service;

import cn.alphahub.common.util.PageUtils;
import cn.alphahub.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 会员
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 18:20:49
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

