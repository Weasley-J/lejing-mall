package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.member.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
public interface MemberService extends IService<Member>, PageService<Member> {

}