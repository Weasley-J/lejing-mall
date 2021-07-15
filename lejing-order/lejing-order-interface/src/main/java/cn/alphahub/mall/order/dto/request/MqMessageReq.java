package cn.alphahub.mall.order.dto.request;

import cn.alphahub.common.core.page.PageDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * MQ消息查询 - 入参
 *
 * @author liuwenjing
 * @version 1.0
 * @date 2021/06/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MqMessageReq extends PageDomain implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 目标交换机
     */
    private String toExchange;
    /**
     * 路由键
     */
    private String routingKey;
    /**
     * 0-新建，1-已发送，2-错误抵达，3-已抵达
     */
    private Integer status;
    /**
     * 创建时间起
     * <ul>
     *     <p>提示：</p>
     *     <li>在yml文件里面配置了{@code spring.mvc.format.date-time}等全局属性，可以不加@DateTimeFormat转换前端提交的字符串日期</li>
     * </ul>
     */
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"})
    private LocalDateTime createTimeStart;
    /**
     * 创建时间始
     * <ul>
     *     <p>提示：</p>
     *     <li>在yml文件里面配置了{@code spring.mvc.format.date-time}等全局属性，可以不加@DateTimeFormat转换前端提交的字符串日期</li>
     * </ul>
     */
    @DateTimeFormat(fallbackPatterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"})
    private LocalDateTime createTimeEnd;
}
