package cn.alphahub.mall.app.pojo.vo;

import cn.alphahub.common.util.IdSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 场地预约-值对象
 *
 * @author lwj
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteReserveVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地id（主键）
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 项目id（同一类场地项目id相同）
     */
    private String projectId;

    /**
     * 场地名称
     */
    private String siteTitle;

    /**
     * 场地的位置描述信息
     */
    private String siteLocationDesc;

    /**
     * 场地所属社区
     */
    private String siteCommunity;

    /**
     * 场地图片列表
     */
    private List<String> imageUrl;

    /**
     * 7日内预约情况，weekReserveCount >= 1，可预约
     */
    private Integer weekReserveCount;

    /**
     * 排序值，数值越大，越靠前
     */
    private Integer sort;
}
