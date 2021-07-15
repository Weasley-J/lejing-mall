package cn.alphahub.mall.app.pojo.bo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 场地预定-BO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteBookBO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 场次id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteSessionId;

    /**
     * 预定日期
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date bookDate;

    /**
     * 最近一场时间 HH:mm
     */
    private String latestTime;

    /**
     * 可预定数量，>=1 可预订
     */
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Integer bookCount;
}
