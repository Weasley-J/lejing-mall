package cn.alphahub.mall.ware.mapper;

import cn.alphahub.mall.ware.domain.Purchase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-07 22:47:37
 */
@Mapper
public interface PurchaseMapper extends BaseMapper<Purchase> {
	
}