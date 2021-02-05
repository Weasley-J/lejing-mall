package cn.alphahub.common.core.top.abstraction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据返回封装顶层抽象类
 *
 * @author liuwenjing
 * @date 2021年2月5日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResult<T> implements Serializable {

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回结果
     */
    private Boolean success = false;

    /**
     * 响应时间戳
     */
    private String timestamp;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应数据对象
     */
    private T data;
}
