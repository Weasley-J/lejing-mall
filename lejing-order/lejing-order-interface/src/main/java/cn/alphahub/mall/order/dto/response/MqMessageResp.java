package cn.alphahub.mall.order.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MQ消息查询 - 响应数据
 *
 * @author liuwe
 * @version 1.0
 * @date 2021/06/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MqMessageResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
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
     * 0-新建，1-已发送，2-错误抵达，3-已抵达
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
}
