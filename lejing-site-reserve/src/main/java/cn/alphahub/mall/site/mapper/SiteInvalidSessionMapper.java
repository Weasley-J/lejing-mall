package cn.alphahub.mall.site.mapper;

import cn.alphahub.mall.site.domain.SiteInvalidSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 场地预约不可用场次表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Mapper
public interface SiteInvalidSessionMapper extends BaseMapper<SiteInvalidSession> {

}
