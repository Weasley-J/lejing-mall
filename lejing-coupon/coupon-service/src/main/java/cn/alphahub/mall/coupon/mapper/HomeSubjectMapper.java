package cn.alphahub.mall.coupon.mapper;

import cn.alphahub.mall.coupon.domain.HomeSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Mapper
public interface HomeSubjectMapper extends BaseMapper<HomeSubject> {
	
}