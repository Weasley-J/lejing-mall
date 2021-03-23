package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enumeration.CheckUserExistsStatus;
import cn.alphahub.mall.member.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 会员Service接口
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
public interface MemberService extends IService<Member> {

    /**
     * 查询会员分页列表
     *
     * @param pageDomain 分页数据
     * @param member     分页对象
     * @return 会员分页数据
     */
    PageResult<Member> queryPage(PageDomain pageDomain, Member member);

    /**
     * 校验会员用户是否注册过,返回相应的提示消息给前端
     *
     * @param member 会员
     * @return 校验会员用户存在状态
     */
    CheckUserExistsStatus checkUserExistsStatus(Member member);
}
