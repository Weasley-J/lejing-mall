package cn.alphahub.mall.site.mapper;

import cn.alphahub.mall.app.pojo.vo.SiteBookVO;
import cn.alphahub.mall.site.domain.SiteReserveStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场地状态表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Mapper
public interface SiteReserveStatusMapper extends BaseMapper<SiteReserveStatus> {

    /**
     * 当前时间起7天内的数据
     *
     * @param siteId site id
     * @param days   How many days of data do you want to get
     * @return EbSiteReserveStatus list
     */
    List<SiteReserveStatus> getDataInDays(@Param("siteId") Long siteId, @Param("days") Integer days);

    /**
     * 当前时间起7天内的数据数量
     *
     * @param siteId site id
     * @param days   How many days of data do you want to get
     * @return count
     */
    int getDataCountInDays(@Param("siteId") Long siteId, @Param("days") Integer days);

    /**
     * 查询七日内最早可预约场次数据
     *
     * @return 场地预定列表
     */
    List<SiteBookVO> getLatestSessionDataInDays(@Param("siteId") Long siteId, @Param("days") Integer days);
}
