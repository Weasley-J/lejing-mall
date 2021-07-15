package cn.alphahub.mall.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 场地预定-值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteBookVO {
    /**
     * 场次id
     */
    private Long siteSessionId;
    /**
     * 生效日期
     */
    private Date effectDate;
    /**
     * 开始时刻
     */
    private String sessionStartTime;
    /**
     * 结束时刻
     */
    private String sessionFinishTime;
}
