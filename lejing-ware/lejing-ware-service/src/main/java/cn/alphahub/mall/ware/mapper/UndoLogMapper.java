package cn.alphahub.mall.ware.mapper;

import cn.alphahub.mall.ware.domain.UndoLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 撤销日志表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 15:19:57
 */
@Mapper
public interface UndoLogMapper extends BaseMapper<UndoLog> {

}
