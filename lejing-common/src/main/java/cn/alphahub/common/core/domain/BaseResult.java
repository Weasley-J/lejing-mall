package cn.alphahub.common.core.domain;

import cn.alphahub.common.constant.HttpStatus;
import cn.alphahub.common.core.page.PageResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 通用Controller数据返回封装
 *
 * @author liuwenjing
 * @date 2021年2月2日22:58:59
 */
@Data
@Builder
@AllArgsConstructor
public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * 初始化一个新创建的 R 对象，使其表示一个空消息
     */
    public BaseResult() {
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public BaseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 R 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public BaseResult(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        if (ObjectUtils.isNotEmpty(data)) {
            this.data = data;
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public BaseResult<T> success() {
        return new BaseResult<T>().success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public BaseResult<T> success(Object data) {
        return new BaseResult<T>().success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public BaseResult<T> success(String msg) {
        return new BaseResult<T>().success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public BaseResult<T> success(String msg, Object data) {
        return new BaseResult<>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public BaseResult<T> error() {
        BaseResult<T> tR = new BaseResult<>();
        return tR.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public BaseResult<T> error(String msg) {
        return new BaseResult<T>().error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public BaseResult<T> error(String msg, Object data) {
        return new BaseResult<>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public BaseResult<T> error(int code, String msg) {
        return new BaseResult<>(code, msg, null);
    }

    /**
     * 返回分页列表
     *
     * @param items      分页对象集合
     * @param totalCount 总记录数
     * @param totalPage  总页数
     * @return 分页数据集合
     */
    public BaseResult<PageResult<T>> queryPage(List<T> items, Long totalCount, Long totalPage) {
        PageResult<T> pageResult = PageResult.<T>builder()
                .totalCount(totalCount)
                .totalPage(totalPage)
                .items(items)
                .build();
        if (Objects.nonNull(pageResult)) {
            return new BaseResult<PageResult<T>>().success("查询成功", pageResult);
        }
        return new BaseResult<PageResult<T>>().error("查询失败");
    }
}