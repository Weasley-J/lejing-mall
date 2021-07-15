package cn.alphahub.mall.app.pojo.bo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * 场地预约场次-BO
 *
 * @author liuwenjing
 * @date 2021-02-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteSessionBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 场地状态id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteStatusId;

    /**
     * 场地场次id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteSessionId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场地名称
     */
    private String siteTitle;

    /**
     * 场次生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;

    /**
     * 场次开始时间点（规则: 24H制，时分中间用:号隔开；如: 08:00, 09:30，可精确到具体分钟）
     */
    @Length(min = 5, max = 5, message = "场次开始时间点长度必须是5位，格式：HH:mm")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "24小时制时间格式不正确，正确格式：HH:mm")
    private String sessionStartTime;

    /**
     * 场次结束时间点（规则: 24H制，时分中间用:号隔开；如: 20:00, 21:30，可精确到具体分钟）
     */
    @Length(min = 5, max = 5, message = "场次开始时间点长度必须是5位，格式：HH:mm")
    @Pattern(regexp = "([01]?[0-9]|2[0-3]):[0-5][0-9]", message = "24小时制时间格式不正确，正确格式：HH:mm")
    private String sessionFinishTime;

    /**
     * 当前场次价格（单位：分，默认值从eb_site_reserve表site_price带过来）
     */
    private Integer currentPrice;
}
