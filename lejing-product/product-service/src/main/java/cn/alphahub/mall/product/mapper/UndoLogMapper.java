package cn.alphahub.mall.product.mapper;

import cn.alphahub.mall.product.domain.UndoLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 撤销日志表
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:39:32
 */
@Mapper
public interface UndoLogMapper extends BaseMapper<UndoLog> {
	
}