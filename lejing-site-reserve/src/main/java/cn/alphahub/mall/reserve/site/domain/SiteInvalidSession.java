package cn.alphahub.mall.reserve.site.domain;

import cn.alphahub.common.util.IdSerializer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 场地预约不可用场次表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_invalid_session")
public class SiteInvalidSession implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long invalidId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场次id（多个场次以","分割，如：1515151,54646484）
     */
    private String siteSessionIds;

    /**
     * 项目编号（场地项目类型）
     */
    private String projectCode;

    /**
     * 项目状态（1全可用，-1全不可用，0部分不可用，默认-1）
     */
    private Integer projectStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 备注信息
     */
    private String remark;

}
