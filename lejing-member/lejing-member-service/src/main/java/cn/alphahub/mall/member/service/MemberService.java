package cn.alphahub.mall.member.service;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enums.CheckUserExistsStatus;
import cn.alphahub.mall.auth.domain.SocialUser;
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

    /**
     * 处理微博社交登录
     *
     * @param socialUser 微博社交用户实体
     * @return 用户信息
     */
    Member loginByWeibo(SocialUser socialUser);

    /**
     * 使用微信的accessToken登录注册用户
     *
     * @param accessTokenInfo 微信accessToken信息
     * @return 用户信息
     */
    Member loginWithWeChat(String accessTokenInfo);
}
