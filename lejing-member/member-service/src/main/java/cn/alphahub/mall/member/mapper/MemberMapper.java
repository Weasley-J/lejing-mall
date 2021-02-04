package cn.alphahub.mall.member.mapper;

import cn.alphahub.mall.member.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
	
}