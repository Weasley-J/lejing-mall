package cn.alphahub.common.pojo;

import com.cf.common.constant.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Controller返回数据封装
 *
 * @author lwj
 */
@Data
@Builder
@AllArgsConstructor
public class ResultSet<T> implements Serializable {
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
     * 初始化一个新创建的 ResultSet 对象，使其表示一个空消息。
     */
    public ResultSet() {
    }

    /**
     * 初始化一个新创建的 ResultSet 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public ResultSet(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 ResultSet 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public ResultSet(int code, String msg, Object data) {
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
    public ResultSet<T> success() {
        return new ResultSet<T>().success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public ResultSet<T> success(Object data) {
        return new ResultSet<T>().success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public ResultSet<T> success(String msg) {
        return new ResultSet<T>().success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public ResultSet<T> success(String msg, Object data) {
        return new ResultSet<>(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public ResultSet<T> error() {
        ResultSet<T> tResultSet = new ResultSet<>();
        return tResultSet.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public ResultSet<T> error(String msg) {
        return new ResultSet<T>().error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public ResultSet<T> error(String msg, Object data) {
        return new ResultSet<>(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public ResultSet<T> error(int code, String msg) {
        return new ResultSet<>(code, msg, null);
    }

    /**
     * 返回分页列表
     *
     * @param list  分页对象集合
     * @param total 总记录数
     * @param pages 总页数
     * @return 分页数据集合
     */
    public ResultSet<PageResult<T>> queryPage(List<T> list, Long total, Long pages) {
        PageResult<T> pageResult = PageResult.<T>builder()
                .total(total)
                .totalPage(pages)
                .items(list)
                .build();
        if (Objects.nonNull(pageResult)) {
            return new ResultSet<PageResult<T>>().success(pageResult);
        }
        return new ResultSet<PageResult<T>>().error();
    }
}