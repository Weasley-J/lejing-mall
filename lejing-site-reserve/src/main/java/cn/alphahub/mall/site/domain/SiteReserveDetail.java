package cn.alphahub.mall.site.domain;

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
 * 场地详情表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-16 18:16:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("eb_site_reserve_detail")
public class SiteReserveDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场地详情id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long detailId;

    /**
     * 场地id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long siteId;

    /**
     * 场地公告信息（管理员发布公告）
     */
    private String siteOfficialNews;

    /**
     * 发布的信息(关联sys_dict_data表的dict_code)
     */
    private Integer sitePubDictCode;

    /**
     * 场地其他信息
     */
    private String sitePubTopic;

    /**
     * 详情是否可用（1：可用，0：不可用，默认：1）
     */
    private Integer isEnable;

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
