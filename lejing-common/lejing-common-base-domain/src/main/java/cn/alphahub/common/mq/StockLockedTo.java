package cn.alphahub.common.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发送到mq消息队列的数据
 *
 * @author liuwenjing
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLockedTo implements Serializable {
    /**
     * 库存工作单的id
     **/
    private Long id;
    /**
     * 工作单详情的所有信息
     **/
    private StockDetailTo detailTo;
}
