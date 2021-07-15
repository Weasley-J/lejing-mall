package cn.alphahub.mall.member.domain;

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
 * 成长值变化历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-24 16:15:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_growth_change_history")
public class GrowthChangeHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    @JsonSerialize(using = IdSerializer.class)
    private Long id;

    /**
     * member_id
     */
    @JsonSerialize(using = IdSerializer.class)
    private Long memberId;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * 改变的值（正负计数）
     */
    private Integer changeCount;

    /**
     * 备注
     */
    private String note;

    /**
     * 积分来源[0-购物，1-管理员修改]
     */
    private Integer sourceType;

}
