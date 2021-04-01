package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enumeration.CheckUserExistsStatus;
import cn.alphahub.mall.auth.domain.SocialUser;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.mapper.MemberMapper;
import cn.alphahub.mall.member.service.MemberService;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会员Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Slf4j
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    /**
     * 查询会员分页列表
     *
     * @param pageDomain 分页数据
     * @param member     分页对象
     * @return 会员分页数据
     */
    @Override
    public PageResult<Member> queryPage(PageDomain pageDomain, Member member) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<Member> wrapper = new QueryWrapper<>(member);
        // 2. 创建一个分页对象
        PageResult<Member> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<Member> memberList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberList);
    }

    /**
     * 校验会员用户是否注册过,返回相应的提示消息给前端
     *
     * @param member 会员
     * @return 校验会员用户存在状态
     */
    @Override
    public CheckUserExistsStatus checkUserExistsStatus(Member member) {
        // 1 member为空就不要查数据库了
        if (Objects.isNull(member)) {
            return CheckUserExistsStatus.USER_IS_EMPTY;
        }
        // 2 校验username是否存在
        QueryWrapper<Member> wrapper1 = new QueryWrapper<>();
        int userAmount = this.count(wrapper1.lambda().eq(Member::getUsername, member.getUsername()));
        if (userAmount > 0) {
            return CheckUserExistsStatus.USERNAME_EXISTS;
        }
        // 3 校验email是否存在
        QueryWrapper<Member> wrapper2 = new QueryWrapper<>();
        userAmount = this.count(wrapper2.lambda().eq(Member::getEmail, member.getEmail()));
        if (userAmount > 0) {
            return CheckUserExistsStatus.EMAIL_EXISTS;
        }
        // 4 校验phone是否存在
        QueryWrapper<Member> wrapper3 = new QueryWrapper<>();
        userAmount = this.count(wrapper3.lambda().eq(Member::getMobile, member.getMobile()));
        if (userAmount > 0) {
            return CheckUserExistsStatus.PHONE_EXISTS;
        }
        // 5 返回用户可以注册
        return CheckUserExistsStatus.USER_CAN_REGISTER;
    }

    /**
     * 处理微博社交登录
     * <p>具有登录和注册逻辑</p>
     *
     * @param socialUser 微博社交用户实体
     * @return 用户信息
     */
    @Override
    public Member loginByWeibo(SocialUser socialUser) {
        if (ObjectUtils.isEmpty(socialUser)) {
            return null;
        }
        String uid = socialUser.getUid();
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        Member member, memberNew = new Member();
        try {
            member = this.getOne(queryWrapper.lambda().eq(Member::getSocialUid, uid));
            // 用户注册过
            if (ObjectUtils.isNotEmpty(member)) {
                BeanUtils.copyProperties(member, memberNew);
                memberNew.setAccessToken(socialUser.getAccess_token());
                memberNew.setExpiresIn(socialUser.getRemind_in());
                this.updateById(memberNew);
                member = memberNew;
            } else {
                // 用户未注册过
                //2、没有查到当前社交用户对应的记录我们就需要注册一个
                Member registerUser = new Member();
                //3、查询当前社交用户的社交账号信息（昵称、性别等）
                Map<String, Object> query = new HashMap<>();
                query.put("access_token", socialUser.getAccess_token());
                query.put("uid", socialUser.getUid());
                // HttpUtil.doGet("https://api.weibo.com/2/users/show.json", "get", new HashMap<String, String>(), query);
                String response = HttpUtil.get("https://api.weibo.com/2/users/show.json", query);
                if (StringUtils.hasText(response)) {
                    // 查询成功
                    JSONObject jsonObject = JSON.parseObject(response);
                    String name = jsonObject.getString("name");
                    String gender = jsonObject.getString("gender");
                    String profileImageUrl = jsonObject.getString("profile_image_url");
                    registerUser.setNickname(name);
                    registerUser.setGender("m".equals(gender) ? 1 : 0);
                    registerUser.setHeader(profileImageUrl);
                    registerUser.setCreateTime(new Date());
                    registerUser.setSocialUid(socialUser.getUid());
                    registerUser.setAccessToken(socialUser.getAccess_token());
                    registerUser.setExpiresIn(String.valueOf(socialUser.getExpires_in()));
                    //把用户信息插入到数据库中
                    this.baseMapper.insert(registerUser);
                    member = registerUser;
                }
            }
        } catch (Exception e) {
            log.error("查询用户失败, 异常原因: {}\n", e.getLocalizedMessage(), e);
            return null;
        }
        return member;
    }
}
