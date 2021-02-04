package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.service.PageService;
import cn.alphahub.mall.member.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
public interface MemberService extends IService<Member>, PageService<Member> {

}