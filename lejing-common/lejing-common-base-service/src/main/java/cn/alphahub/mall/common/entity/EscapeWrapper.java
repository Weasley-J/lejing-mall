package cn.alphahub.mall.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * result包装逃逸，返回data里面的原对象
 **/
@Data
public final class EscapeWrapper<T> implements Serializable {
    /**
     * 数据载荷
     */
    private T data;

    /**
     * 直接返回逃逸结果Data里面的对象
     */
    public static <T> EscapeWrapper<T> of(T data) {
        EscapeWrapper<T> result = new EscapeWrapper<>();
        result.setData(data);
        return result;
    }
}
