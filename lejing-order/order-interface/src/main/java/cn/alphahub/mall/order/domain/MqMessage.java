package cn.alphahub.mall.order.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * MQ消息表
 *
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-02-06 02:43:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("mq_message")
public class MqMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息id
	 */
    @TableId
    private String messageId;

	/**
	 * 内容
	 */
    private String content;

	/**
	 * 目标交换机
	 */
    private String toExchange;

	/**
	 * 路由键
	 */
    private String routingKey;

	/**
	 * 全限定类名
	 */
    private String classType;

	/**
	 * 0-新建1-已发送2错误抵达3-已抵达
	 */
    private Integer messageStatus;

	/**
	 * 创建时间
	 */
    private Date createTime;

	/**
	 * 更新时间
	 */
    private Date updateTime;

}
