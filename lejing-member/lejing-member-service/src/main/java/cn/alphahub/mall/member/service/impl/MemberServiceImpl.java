package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.common.enumeration.CheckUserExistsStatus;
import cn.alphahub.mall.member.domain.Member;
import cn.alphahub.mall.member.mapper.MemberMapper;
import cn.alphahub.mall.member.service.MemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 会员Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
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
}
