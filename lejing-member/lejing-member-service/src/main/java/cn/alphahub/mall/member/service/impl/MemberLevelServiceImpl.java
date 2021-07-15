package cn.alphahub.mall.member.service.impl;

import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;
import cn.alphahub.mall.member.domain.MemberLevel;
import cn.alphahub.mall.member.mapper.MemberLevelMapper;
import cn.alphahub.mall.member.service.MemberLevelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员等级Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {

    /**
     * 查询会员等级分页列表
     *
     * @param pageDomain  分页数据
     * @param memberLevel 分页对象
     * @return 会员等级分页数据
     */
    @Override
    public PageResult<MemberLevel> queryPage(PageDomain pageDomain, MemberLevel memberLevel) {
        // 1. 构造mybatis-plus查询wrapper
        QueryWrapper<MemberLevel> wrapper = new QueryWrapper<>(memberLevel);
        // 2. 创建一个分页对象
        PageResult<MemberLevel> pageResult = new PageResult<>();
        // 3. 开始分页
        pageResult.startPage(pageDomain);
        // 4. 执行Dao|Mapper SQL查询
        List<MemberLevel> memberLevelList = this.list(wrapper);
        // 5. 分装并返回数据
        return pageResult.getPage(memberLevelList);
    }

}
