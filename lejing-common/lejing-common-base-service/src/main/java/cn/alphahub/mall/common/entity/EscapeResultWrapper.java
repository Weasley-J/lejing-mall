package cn.alphahub.mall.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * result包装逃逸
 **/
@Data
public final class EscapeResultWrapper<T> implements Serializable {
    private T data;
}
