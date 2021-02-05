package cn.alphahub.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import cn.alphahub.common.core.page.PageDomain;
import cn.alphahub.common.core.page.PageResult;

import cn.alphahub.mall.member.mapper.MemberLoginLogMapper;
import cn.alphahub.mall.member.domain.MemberLoginLog;
import cn.alphahub.mall.member.service.MemberLoginLogService;

import java.util.List;

/**
 * 会员登录记录Service业务层处理
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:45:32
 */
@Service("memberLoginLogService")
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLog> implements MemberLoginLogService {

    /**
     * 查询会员登录记录分页列表
     *
     * @param pageDomain   分页数据
     * @param memberLoginLog 分页对象
     * @return 会员登录记录分页数据
     */
    @Override
    public PageResult<MemberLoginLog> queryPage(PageDomain pageDomain, MemberLoginLog memberLoginLog) {
        pageDomain.startPage();
        QueryWrapper<MemberLoginLog> wrapper = new QueryWrapper<>(memberLoginLog);
        List<MemberLoginLog> list = this.list(wrapper);
        PageInfo<MemberLoginLog> pageInfo = new PageInfo<>(list);
        PageResult<MemberLoginLog> pageResult = PageResult.<MemberLoginLog>builder()
                .totalCount(pageInfo.getTotal())
                .totalPage((long) pageInfo.getPages())
                .items(pageInfo.getList())
                .build();
        return pageResult;
    }

}