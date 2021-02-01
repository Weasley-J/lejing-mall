package cn.alphahub.mall.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Weasley J
 * @email 1432689025@qq.com
 * @date 2021-01-31 17:56:09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("mq_message")
public class MqMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private String messageId;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private String toExchange;
    /**
     *
     */
    private String routingKey;
    /**
     *
     */
    private String classType;
    /**
     * 0-新建1-已发送2错误抵达3-已抵达
     */
    private Integer messageStatus;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

}
