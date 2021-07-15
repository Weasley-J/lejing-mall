package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import cn.alphahub.mall.member.mapper.MemberLoginLogMapper;
import cn.alphahub.mall.member.service.MemberLoginLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员登录记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {

    /**
     * 查询会员登录记录分页列表
     *
     * @param pageDomain     分页数据
     * @param memberLoginLog 分页对象
     * @return 会员登录记录分页数据
     */
    @Override
    public PageResult<MemberLoginLog> queryPage(PageDomain pageDomain, MemberLoginLog memberLoginLog) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberLoginLog> wrapper = new QueryWrapper<>(memberLoginLog);
        // 2. 创建一个分页对象
        PageResult<MemberLoginLog> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberLoginLog> memberLoginLogList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberLoginLogList);
    }

}
