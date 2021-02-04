package cn.alphahub.mall.member.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分变化历史记录
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-05 02:14:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ums_integration_change_history")
public class IntegrationChangeHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
    @TableId
    private Long id;

	/**
	 * member_id
	 */
    private Long memberId;

	/**
	 * create_time
	 */
    private Date createTime;

	/**
	 * 变化的值
	 */
    private Integer changeCount;

	/**
	 * 备注
	 */
    private String note;

	/**
	 * 来源[0->购物；1->管理员修改;2->活动]
	 */
    private Integer sourceTyoe;

}