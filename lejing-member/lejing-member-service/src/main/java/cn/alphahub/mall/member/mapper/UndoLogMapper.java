package cn.alphahub.mall.member.mapper;

import cn.alphahub.mall.member.domain.UndoLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 撤销日志表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:43:41
 */
@Mapper
public interface UndoLogMapper extends BaseMapper<UndoLog> {

}
