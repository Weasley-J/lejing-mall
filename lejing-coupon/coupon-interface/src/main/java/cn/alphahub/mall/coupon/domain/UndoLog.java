package cn.alphahub.mall.coupon.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 撤销日志表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:47:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("undo_log")
public class UndoLog implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
    @TableId
    private Long id;

	/**
	 * 相关的branch id
	 */
    private Long branchId;

	/**
	 * 相关的xid
	 */
    private String xid;

	/**
	 * 内容
	 */
    private String context;

	/**
	 * 回滚信息
	 */
    private String rollbackInfo;

	/**
	 * 日志状态码
	 */
    private Integer logStatus;

	/**
	 * 日志创建时间
	 */
    private Date logCreated;

	/**
	 * 日志修改时间
	 */
    private Date logModified;

	/**
	 * 其他信息
	 */
    private String ext;

}
