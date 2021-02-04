package cn.alphahub.mall.coupon.mapper;

import cn.alphahub.mall.coupon.domain.UndoLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 撤销日志表
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:10:59
 */
@Mapper
public interface UndoLogMapper extends BaseMapper<UndoLog> {
	
}